package com.growwithme.crops.interfaces.rest.transform;

import com.growwithme.crops.domain.model.commands.UpdateCropCommand;
import com.growwithme.crops.interfaces.rest.resources.UpdateCropResource;

public class UpdateCropCommandFromResourceAssembler {
    public static UpdateCropCommand toCommandFromResource(Long id, UpdateCropResource resource) {
        return new UpdateCropCommand(
                id,
                resource.productName(),
                resource.code(),
                resource.category(),
                resource.area(),
                resource.location(),
                resource.cost()
        );
    }
}
