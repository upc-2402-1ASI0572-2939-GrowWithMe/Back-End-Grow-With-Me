package com.growwithme.devices.interfaces.acl;

import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.domain.model.valueobjects.DeviceType;

import java.util.List;
import java.util.Optional;

public interface DeviceContextFacade {
    Long createDevice(Long cropId, Long farmerId, String name, DeviceType deviceType);

    List<Device> fetchAllDevicesByFarmerId(Long farmerId);

    Optional<Device> fetchDeviceById(Long id);
}
