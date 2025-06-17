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

import java.com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import java.com.growwithme.crops.domain.services.CropActivityCommandService;
import java.com.growwithme.crops.domain.services.CropActivityQueryService;
import java.com.growwithme.crops.interfaces.rest.resources.CreateCropActivityResource;
import java.com.growwithme.crops.interfaces.rest.resources.CropActivityResource;
import java.com.growwithme.crops.interfaces.rest.transform.CreateCropActivityCommandFromResourceAssembler;
import java.com.growwithme.crops.interfaces.rest.transform.CropActivityResourceFromEntityAssembler;

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

    
}
