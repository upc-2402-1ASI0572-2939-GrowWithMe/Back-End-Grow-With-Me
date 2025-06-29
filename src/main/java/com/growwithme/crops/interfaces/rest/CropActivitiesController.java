package com.growwithme.crops.interfaces.rest;

import com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import com.growwithme.crops.domain.services.CropQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import com.growwithme.crops.domain.model.queries.GetAllCropActivitiesByCropIdQuery;
import com.growwithme.crops.domain.services.CropActivityCommandService;
import com.growwithme.crops.domain.services.CropActivityQueryService;
import com.growwithme.crops.interfaces.rest.resources.CreateCropActivityResource;
import com.growwithme.crops.interfaces.rest.resources.CropActivityResource;
import com.growwithme.crops.interfaces.rest.resources.UpdateCropActivityResource;
import com.growwithme.crops.interfaces.rest.transform.CreateCropActivityCommandFromResourceAssembler;
import com.growwithme.crops.interfaces.rest.transform.CropActivityResourceFromEntityAssembler;
import com.growwithme.crops.interfaces.rest.transform.UpdateCropActivityCommandFromResourceAssembler;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/crop-activities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Crop Activities", description = "Crop Activities Management Endpoint")

public class CropActivitiesController {
    private final CropActivityCommandService commandService;
    private final CropActivityQueryService queryService;
    private final CropQueryService cropQueryService;

    @Operation(summary = "Create a new crop activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crop activity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @PostMapping
    public ResponseEntity<CropActivityResource> createCropActivity(@RequestParam("cropId") Long cropId, @RequestParam("activityDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate activityDate, @RequestParam("description") String description) {
        var cropResult = cropQueryService.handle(new GetCropByIdQuery(cropId));

        if (cropResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        var crop = cropResult.get();

        var createCropActivityCommand = new CreateCropActivityCommand(
                crop.getId(),
                activityDate,
                description
        );

        var cropActivity = commandService.handle(createCropActivityCommand);

        if (cropActivity.isEmpty()) {
            throw new IllegalArgumentException("Invalid input data for crop activity creation");
        }

        var cropActivityResource = CropActivityResourceFromEntityAssembler.toResourceFromEntity(cropActivity.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(cropActivityResource);
    }

    @Operation(summary = "Delete a crop activity by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activity not found"),
    })
    @DeleteMapping("/{cropActivityId}")
    public ResponseEntity<?> deleteCropActivity(@PathVariable Long cropActivityId) {
        var deleteCropActivityCommand = new DeleteCropActivityCommand(cropActivityId);
        commandService.handle(deleteCropActivityCommand);
        return ResponseEntity.ok("Crop activity deleted successfully");
    }

    @Operation(summary = "Delete all crop activities by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All crop activities deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activities not found for the given crop ID")
    })
    @DeleteMapping("/{cropId}")
    public ResponseEntity<?> deleteAllCropActivitiesByCropId(@PathVariable Long cropId) {
        var deleteCropActivitiesCommand = new DeleteAllCropActivitiesByCropIdCommand(cropId);
        commandService.handle(deleteCropActivitiesCommand);
        return ResponseEntity.ok("All crop activities deleted successfully");
    }

    @Operation(summary = "Update a crop activity by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Crop activity not found")
    })
    @PutMapping("/{cropActivityId}")
    public ResponseEntity<CropActivityResource> updateCropActivity(@PathVariable Long cropActivityId, @RequestBody UpdateCropActivityResource resource) {
        var updateCropActivityCommand = UpdateCropActivityCommandFromResourceAssembler.toCommandFromResource(cropActivityId, resource);
        var updatedCropActivity = commandService.handle(updateCropActivityCommand);
        if (updatedCropActivity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropActivityResource = CropActivityResourceFromEntityAssembler.toResourceFromEntity(updatedCropActivity.get());
        return ResponseEntity.ok(cropActivityResource);
    }

    @Operation(summary = "Get all crop activities by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activities retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activities not found")
    })
    @GetMapping("/{cropId}")
    public ResponseEntity<List<CropActivityResource>> getAllCropActivitiesByCropId(@PathVariable Long cropId) {
        var query = new GetAllCropActivitiesByCropIdQuery(cropId);
        var cropActivities = queryService.handle(query);
        if (cropActivities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cropActivityResources = cropActivities.stream()
                .map(CropActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(cropActivityResources);
    }
}
