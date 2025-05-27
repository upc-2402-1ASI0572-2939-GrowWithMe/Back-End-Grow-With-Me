package java.com.growwithme.crops.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.com.growwithme.crops.domain.model.commands.DeleteCropCommand;
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
@RestController
@RequestMapping(value = "/api/v1/crops", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Crops", description = "Crops Management Endpoint")
public class CropsController {
    private final CropCommandService cropCommandService;
    private final CropQueryService cropQueryService;

    @PostMapping
    public ResponseEntity<CropResource> createCrop(@RequestBody CreateCropResource resource) {
        var createCropCommand = CreateCropCommandFromResourceAssembler.toCommandFromResource(resource);
        var crop = cropCommandService.handle(createCropCommand);
        if (crop.isEmpty()) { return ResponseEntity.badRequest().build(); }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return new ResponseEntity<>(cropResource, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        var deleteCropCommand = new DeleteCropCommand(id);
        cropCommandService.handle(deleteCropCommand);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CropResource> updateCrop(@PathVariable Long id, @RequestBody UpdateCropResource resource) {
        var updateCropCommand = UpdateCropCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedCrop = cropCommandService.handle(updateCropCommand);
        if (updatedCrop.isEmpty()) { return ResponseEntity.badRequest().build(); }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(updatedCrop.get());
        return ResponseEntity.ok(cropResource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CropResource> getCropById(@PathVariable Long id) {
        var getCropByIdQuery = new GetCropByIdQuery(id);
        var crop = cropQueryService.handle(getCropByIdQuery);
        if (crop.isEmpty()) { return ResponseEntity.notFound().build(); }
        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return ResponseEntity.ok(cropResource);
    }

    @GetMapping
    public ResponseEntity<List<CropResource>> getAllCrops() {
        var getAllCropsQuery = new GetAllCropsQuery();
        var crops = cropQueryService.handle(getAllCropsQuery);
        if (crops.isEmpty()) { return ResponseEntity.notFound().build(); }
        var cropResources = crops.stream()
                .map(CropResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(cropResources);
    }
}
