package com.growwithme.devices.domain.model.commands;

import com.growwithme.devices.domain.model.valueobjects.DeviceType;

public record CreateDeviceCommand(Long cropId, Long farmerId, String name, DeviceType deviceType) {
}
