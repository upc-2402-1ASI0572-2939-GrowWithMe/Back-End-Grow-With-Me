package com.growwithme.devices.interfaces.rest.resources;

import java.util.List;

public record DeviceSensorDataResource(
        List<Float> temperatureList,
        List<Float> humidityList
) {
}
