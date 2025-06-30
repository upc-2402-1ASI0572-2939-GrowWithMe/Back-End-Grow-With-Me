package com.growwithme.devices.interfaces.rest.transform;

import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.interfaces.rest.resources.DeviceResource;

public class DeviceResourceFromEntityAssembler {
    public static DeviceResource toResourceFromEntity(Device entity) {
        return new DeviceResource(
                entity.getId(),
                entity.getCrop().getId(),
                entity.getFarmerUser().getId(),
                entity.getName()
        );
    }
}
