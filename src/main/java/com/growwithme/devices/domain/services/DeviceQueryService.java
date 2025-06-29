package com.growwithme.devices.domain.services;

import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.domain.model.queries.GetAllDevicesByFarmerIdQuery;
import com.growwithme.devices.domain.model.queries.GetDeviceByIdQuery;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    Optional<Device> handle(GetDeviceByIdQuery query);
    List<Device> handle(GetAllDevicesByFarmerIdQuery query);
}
