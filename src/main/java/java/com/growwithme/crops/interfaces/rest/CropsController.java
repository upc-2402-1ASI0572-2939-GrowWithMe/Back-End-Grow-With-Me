package java.com.growwithme.crops.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import java.com.growwithme.crops.domain.model.queries.GetAllCropsByFarmerIdQuery;
import java.com.growwithme.crops.domain.model.queries.GetAllCropsQuery;
import java.com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import java.com.growwithme.crops.domain.services.CropCommandService;
import java.com.growwithme.crops.domain.services.CropQueryService;
import java.com.growwithme.crops.interfaces.rest.resources.CreateCropResource;
import java.com.growwithme.crops.interfaces.rest.resources.CropResource;
import java.com.growwithme.crops.interfaces.rest.resources.UpdateCropResource;
import java.com.growwithme.crops.interfaces.rest.transform.CreateCropCommandFromResourceAssembler;
import java.com.growwithme.crops.interfaces.rest.transform.CropResourceFromEntityAssembler;
import java.com.growwithme.crops.interfaces.rest.transform.UpdateCropCommandFromResourceAssembler;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/crops", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Crops", description = "Crops Management Endpoint")
public class CropsController {
    private final CropCommandService cropCommandService;
    private final CropQueryService cropQueryService;

    @Operation(summary = "Create a new crop")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crop created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CropResource> createCrop(@RequestBody CreateCropResource resource) {
        var createCropCommand = CreateCropCommandFromResourceAssembler.toCommandFromResource(resource);
        var crop = cropCommandService.handle(createCropCommand);
        if (crop.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return new ResponseEntity<>(cropResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a crop by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Crop deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrop(@PathVariable Long id) {
        var deleteCropCommand = new DeleteCropCommand(id);
        cropCommandService.handle(deleteCropCommand);
        return ResponseEntity.ok("Crop deleted successfully");
    }

    @Operation(summary = "Update a crop by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Crop not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CropResource> updateCrop(@PathVariable Long id, @RequestBody UpdateCropResource resource) {
        var updateCropCommand = UpdateCropCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedCrop = cropCommandService.handle(updateCropCommand);
        if (updatedCrop.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(updatedCrop.get());
        return ResponseEntity.ok(cropResource);
    }

    @Operation(summary = "Get a crop by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop found"),
            @ApiResponse(responseCode = "404", description = "Crop not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CropResource> getCropById(@PathVariable Long id) {
        var getCropByIdQuery = new GetCropByIdQuery(id);
        var crop = cropQueryService.handle(getCropByIdQuery);
        if (crop.isEmpty()) { return ResponseEntity.badRequest().build(); }
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
        if (crops.isEmpty()) { return ResponseEntity.badRequest().build(); }
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
        if (crops.isEmpty()) { return ResponseEntity.badRequest().build(); }
        var cropResources = crops.stream()
                .map(CropResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(cropResources);
    }
}
