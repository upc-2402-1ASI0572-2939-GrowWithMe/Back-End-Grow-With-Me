package com.growwithme.devices.domain.services;

import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.domain.model.commands.CreateDeviceCommand;
import com.growwithme.devices.domain.model.commands.DeleteDeviceCommand;

import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(CreateDeviceCommand command);
    void handle(DeleteDeviceCommand command);
}
