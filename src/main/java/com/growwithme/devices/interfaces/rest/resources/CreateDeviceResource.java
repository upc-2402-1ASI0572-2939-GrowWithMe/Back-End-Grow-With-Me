package com.growwithme.devices.interfaces.rest.resources;

public record CreateDeviceResource(
        Long cropId,
        Long farmerId,
        String name
) {
}
