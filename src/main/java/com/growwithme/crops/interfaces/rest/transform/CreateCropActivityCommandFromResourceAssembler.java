package com.growwithme.crops.interfaces.rest.transform;

import com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import com.growwithme.crops.interfaces.rest.resources.CreateCropActivityResource;

public class CreateCropActivityCommandFromResourceAssembler {
    public static CreateCropActivityCommand toCommandFromResource(CreateCropActivityResource resource) {
        return new CreateCropActivityCommand(
                resource.cropId(),
                resource.activityDate(),
                resource.description()
        );
    }
}
