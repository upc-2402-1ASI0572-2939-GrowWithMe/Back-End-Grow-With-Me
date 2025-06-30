package com.growwithme.devices.domain.model.commands;

public record CreateDeviceCommand(Long cropId, Long farmerId, String name) {
}
