package com.growwithme.devices.interfaces.rest.resources;

public record DeviceResource(
        Long id,
        Long cropId,
        Long farmerId,
        String name
) {
}
