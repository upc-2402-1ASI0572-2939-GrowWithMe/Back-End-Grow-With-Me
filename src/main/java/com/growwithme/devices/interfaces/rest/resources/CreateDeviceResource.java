package com.growwithme.devices.interfaces.rest.resources;

import com.growwithme.devices.domain.model.valueobjects.DeviceType;

public record CreateDeviceResource(
        Long cropId,
        Long farmerId,
        String name,
        DeviceType deviceType
) {
}
