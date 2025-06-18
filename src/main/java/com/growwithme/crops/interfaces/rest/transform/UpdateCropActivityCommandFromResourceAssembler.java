package com.growwithme.crops.interfaces.rest.transform;

import com.growwithme.crops.domain.model.commands.UpdateCropActivityCommand;
import com.growwithme.crops.interfaces.rest.resources.UpdateCropActivityResource;

public class UpdateCropActivityCommandFromResourceAssembler {
    public static UpdateCropActivityCommand toCommandFromResource(Long id, UpdateCropActivityResource resource) {
        return new UpdateCropActivityCommand(
                id,
                resource.activityDate(),
                resource.description()
        );
    }
}
