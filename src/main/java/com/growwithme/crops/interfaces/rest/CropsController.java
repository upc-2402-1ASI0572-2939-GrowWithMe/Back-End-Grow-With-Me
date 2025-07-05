package com.growwithme.crops.interfaces.rest;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import com.growwithme.crops.domain.model.commands.CreateCropCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import com.growwithme.crops.domain.model.events.CropStatusFromHarvestedToEmptyEvent;
import com.growwithme.crops.domain.model.queries.GetAllCropsByFarmerIdQuery;
import com.growwithme.crops.domain.model.queries.GetAllCropsQuery;
import com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import com.growwithme.crops.domain.services.CropCommandService;
import com.growwithme.crops.domain.services.CropQueryService;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import com.growwithme.crops.interfaces.rest.resources.CropResource;
import com.growwithme.crops.interfaces.rest.resources.CreateCropResource;
import com.growwithme.crops.interfaces.rest.resources.UpdateCropResource;
import com.growwithme.crops.interfaces.rest.transform.CropResourceFromEntityAssembler;
import com.growwithme.crops.interfaces.rest.transform.UpdateCropCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/crops", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Crops", description = "Crops Management Endpoint")
public class CropsController {

    private final CropCommandService cropCommandService;
    private final CropQueryService cropQueryService;
    private final CropRepository repository;
    private final ExternalIamService externalIamService;
    private final ApplicationEventPublisher eventPublisher;

    @Operation(summary = "Create a new crop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crop created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropResource> createCrop(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateCropResource resource) {

        var email = userDetails.getUsername();
        var farmerId = externalIamService.fetchUserIdByEmail(email);

        if (farmerId == null) {
            throw new IllegalArgumentException("Farmer not found for email: " + email);
        }

        var createCropCommand = new CreateCropCommand(
                farmerId,
                resource.productName(),
                resource.code(),
                resource.category(),
                resource.area(),
                resource.location()
        );

        var crop = cropCommandService.handle(createCropCommand);

        if (crop.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data for creating crop");
        }

        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return new ResponseEntity<>(cropResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a crop by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Crop deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop not found")
    })
    @DeleteMapping("/{cropId}")
    public ResponseEntity<?> deleteCrop(@PathVariable Long cropId) {
        var deleteCropCommand = new DeleteCropCommand(cropId);
        cropCommandService.handle(deleteCropCommand);

        var response = java.util.Map.of("message", "Crop deleted successfully");
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update a crop by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Crop not found")
    })
    @PutMapping("/{cropId}")
    public ResponseEntity<CropResource> updateCrop(@PathVariable Long cropId, @RequestBody UpdateCropResource resource) {
        var updateCropCommand = UpdateCropCommandFromResourceAssembler.toCommandFromResource(cropId, resource);
        var updatedCrop = cropCommandService.handle(updateCropCommand);
        if (updatedCrop.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(updatedCrop.get());
        return ResponseEntity.ok(cropResource);
    }

    @Operation(summary = "Get a crop by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop found"),
            @ApiResponse(responseCode = "404", description = "Crop not found")
    })
    @GetMapping("/{cropId}")
    public ResponseEntity<CropResource> getCropById(@PathVariable Long cropId) {
        var getCropByIdQuery = new GetCropByIdQuery(cropId);
        var crop = cropQueryService.handle(getCropByIdQuery);
        if (crop.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return ResponseEntity.ok(cropResource);
    }

    @Operation(summary = "Get all crops")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crops found"),
            @ApiResponse(responseCode = "404", description = "No crops found")
    })
    @GetMapping
    public ResponseEntity<List<CropResource>> getAllCrops() {
        var getAllCropsQuery = new GetAllCropsQuery();
        var crops = cropQueryService.handle(getAllCropsQuery);
        if (crops.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropResources = crops.stream()
                .map(CropResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(cropResources);
    }

    @Operation(summary = "Get all crops by farmer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crops found for the farmer"),
            @ApiResponse(responseCode = "404", description = "No crops found for the farmer")
    })
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<CropResource>> getAllCropsByFarmerId(@PathVariable Long farmerId) {
        var getAllCropsQuery = new GetAllCropsByFarmerIdQuery(farmerId);
        var crops = cropQueryService.handle(getAllCropsQuery);

        if (crops.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var cropResources = crops.stream()
                .map(CropResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(cropResources);
    }

    @Operation(summary = "Set crop status to EMPTY")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop status set to EMPTY"),
            @ApiResponse(responseCode = "404", description = "Crop not found"),
            @ApiResponse(responseCode = "400", description = "Crop must be in HARVESTED status to transition to EMPTY")
    })
    @PutMapping("/set-empty/{cropId}")
    public ResponseEntity<?> setCropToEmpty(@PathVariable Long cropId) {
        var cropResult = cropQueryService.handle(new GetCropByIdQuery(cropId));
        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Crop with ID " + cropId + " does not exist.");
        }

        var crop = cropResult.get();
        if (crop.getStatus() != CropStatus.HARVESTED) {
            return ResponseEntity.badRequest().body("Crop must be in HARVESTED status to transition to EMPTY.");
        }

        eventPublisher.publishEvent(new CropStatusFromHarvestedToEmptyEvent(this, cropId, CropStatus.HARVESTED, CropStatus.EMPTY));
        return ResponseEntity.ok("Crop status set to EMPTY successfully.");
    }
}
