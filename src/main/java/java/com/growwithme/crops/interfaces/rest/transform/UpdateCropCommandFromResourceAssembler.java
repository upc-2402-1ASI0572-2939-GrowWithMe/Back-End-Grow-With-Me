package java.com.growwithme.crops.interfaces.rest.transform;

import java.com.growwithme.crops.domain.model.commands.UpdateCropCommand;
import java.com.growwithme.crops.interfaces.rest.resources.UpdateCropResource;

public class UpdateCropCommandFromResourceAssembler {
    public static UpdateCropCommand toCommandFromResource(Long id, UpdateCropResource resource) {
        return new UpdateCropCommand(
                id,
                resource.name(),
                resource.code(),
                resource.category(),
                resource.area(),
                resource.location(),
                resource.status(),
                resource.cost(),
                resource.profitReturn()
        );
    }
}
