package com.growwithme.crops.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/crop_activities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Crop Activities", description = "Crop Activities Management Endpoint")

public class CropActivitiesController {
    private final CropActivityCommandService commandService;
    private final CropActivityQueryService queryService;

    @Operation(summary = "Create a new crop activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Crop activity created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
    })
    @PostMapping
    public ResponseEntity<CropActivityResource> createCropActivity(@RequestBody CreateCropActivityResource resource) {
        var cropActivityCommand = CreateCropActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var cropActivity = commandService.handle(cropActivityCommand);
        if (cropActivity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropActivityResource = CropActivityResourceFromEntityAssembler.toResourceFromEntity(cropActivity.get());
        return new ResponseEntity<>(cropActivityResource, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a crop activity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activity not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCropActivity(@PathVariable Long id) {
        var deleteCropActivityCommand = new DeleteCropActivityCommand(id);
        commandService.handle(deleteCropActivityCommand);
        return ResponseEntity.ok("Crop activity deleted successfully");
    }

    @Operation(summary = "Delete all crop activities by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All crop activities deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activities not found for the given crop ID")
    })
    @DeleteMapping("/crops/{cropId}")
    public ResponseEntity<?> deleteAllCropActivitiesByCropId(@PathVariable Long cropId) {
        var deleteCropActivitiesCommand = new DeleteAllCropActivitiesByCropIdCommand(cropId);
        commandService.handle(deleteCropActivitiesCommand);
        return ResponseEntity.ok("All crop activities deleted successfully");
    }

    @Operation(summary = "Update a crop activity by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Crop activity not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CropActivityResource> updateCropActivity(@PathVariable Long id, @RequestBody UpdateCropActivityResource resource) {
        var updateCropActivityCommand = UpdateCropActivityCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedCropActivity = commandService.handle(updateCropActivityCommand);
        if (updatedCropActivity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var cropActivityResource = CropActivityResourceFromEntityAssembler.toResourceFromEntity(updatedCropActivity.get());
        return ResponseEntity.ok(cropActivityResource);
    }

    @Operation(summary = "Get all crop activities by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activities retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activities not found")
    })
    @GetMapping("/crops/{cropId}")
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
