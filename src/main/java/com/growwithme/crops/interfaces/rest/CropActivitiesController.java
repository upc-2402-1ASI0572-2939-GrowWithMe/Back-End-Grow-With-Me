package com.growwithme.crops.interfaces.rest;

import com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import com.growwithme.crops.domain.model.queries.GetAllCropActivitiesByCropIdQuery;
import com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import com.growwithme.crops.domain.services.CropActivityCommandService;
import com.growwithme.crops.domain.services.CropActivityQueryService;
import com.growwithme.crops.domain.services.CropQueryService;
import com.growwithme.crops.interfaces.rest.resources.CropActivityResource;
import com.growwithme.crops.interfaces.rest.resources.UpdateCropActivityResource;
import com.growwithme.crops.interfaces.rest.transform.CropActivityResourceFromEntityAssembler;
import com.growwithme.crops.interfaces.rest.transform.UpdateCropActivityCommandFromResourceAssembler;
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
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CropActivityResource> createCropActivity(@RequestBody CreateCropActivityCommand command) {

        var cropResult = cropQueryService.handle(new GetCropByIdQuery(command.cropId()));
        if (cropResult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        var cropActivity = commandService.handle(command);
        if (cropActivity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var resource = CropActivityResourceFromEntityAssembler.toResourceFromEntity(cropActivity.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }


    @Operation(summary = "Delete a crop activity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activity deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activity not found")
    })
    @DeleteMapping("/{cropActivityId}")
    public ResponseEntity<?> deleteCropActivity(@PathVariable Long cropActivityId) {
        var command = new DeleteCropActivityCommand(cropActivityId);
        commandService.handle(command);
        var response = java.util.Map.of("message", "Crop activity deleted successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete all crop activities by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All crop activities deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Crop activities not found")
    })
    @DeleteMapping("/crops/{cropId}")
    public ResponseEntity<?> deleteAllCropActivitiesByCropId(@PathVariable Long cropId) {
        var command = new DeleteAllCropActivitiesByCropIdCommand(cropId);
        commandService.handle(command);
        return ResponseEntity.ok("All crop activities deleted successfully");
    }

    @Operation(summary = "Update a crop activity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Crop activity not found")
    })
    @PutMapping("/{cropActivityId}")
    public ResponseEntity<CropActivityResource> updateCropActivity(
            @PathVariable Long cropActivityId,
            @RequestBody UpdateCropActivityResource resource) {

        var command = UpdateCropActivityCommandFromResourceAssembler.toCommandFromResource(cropActivityId, resource);
        var updatedActivity = commandService.handle(command);

        if (updatedActivity.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var activityResource = CropActivityResourceFromEntityAssembler.toResourceFromEntity(updatedActivity.get());
        return ResponseEntity.ok(activityResource);
    }

    @Operation(summary = "Get all crop activities by crop ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crop activities retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No crop activities found")
    })
    @GetMapping("/crops/{cropId}")
    public ResponseEntity<List<CropActivityResource>> getAllCropActivitiesByCropId(@PathVariable Long cropId) {
        var query = new GetAllCropActivitiesByCropIdQuery(cropId);
        var cropActivities = queryService.handle(query);

        if (cropActivities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var resources = cropActivities.stream()
                .map(CropActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }
}
