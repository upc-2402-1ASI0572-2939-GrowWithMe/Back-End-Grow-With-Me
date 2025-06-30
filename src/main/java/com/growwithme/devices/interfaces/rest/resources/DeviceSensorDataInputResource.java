package com.growwithme.devices.interfaces.rest.resources;

public record DeviceSensorDataInputResource(
        Float temperature,
        Float humidity
) {
}
