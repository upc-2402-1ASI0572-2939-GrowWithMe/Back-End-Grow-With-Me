package java.com.growwithme.crops.interfaces.rest.transform;

import java.com.growwithme.crops.domain.model.commands.CreateCropCommand;
import java.com.growwithme.crops.interfaces.rest.resources.CreateCropResource;

public class CreateCropCommandFromResourceAssembler {
    public static CreateCropCommand toCommandFromResource(CreateCropResource resource) {
        return new CreateCropCommand(
                resource.farmerId(),
                resource.productName(),
                resource.code(),
                resource.category(),
                resource.status(),
                resource.area(),
                resource.location(),
                resource.cost()
        );
    }
}
