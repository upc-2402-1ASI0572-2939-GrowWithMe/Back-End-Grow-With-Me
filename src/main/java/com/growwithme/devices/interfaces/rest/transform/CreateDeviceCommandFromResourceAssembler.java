package com.growwithme.devices.interfaces.rest.transform;

import com.growwithme.devices.domain.model.commands.CreateDeviceCommand;
import com.growwithme.devices.interfaces.rest.resources.CreateDeviceResource;

public class CreateDeviceCommandFromResourceAssembler {
    public static CreateDeviceCommand toCommandFromResource(CreateDeviceResource resource) {
        return new CreateDeviceCommand(
                resource.cropId(),
                resource.farmerId(),
                resource.name()
        );
    }
}
