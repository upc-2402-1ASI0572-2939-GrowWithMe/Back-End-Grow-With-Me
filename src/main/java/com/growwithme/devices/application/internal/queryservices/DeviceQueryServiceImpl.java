package com.growwithme.devices.application.internal.queryservices;

import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.domain.model.queries.GetAllDevicesByFarmerIdQuery;
import com.growwithme.devices.domain.model.queries.GetDeviceByIdQuery;
import com.growwithme.devices.domain.services.DeviceQueryService;
import com.growwithme.devices.infrastructure.persistence.jpa.repositories.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceQueryServiceImpl implements DeviceQueryService {
    private final DeviceRepository repository;

    @Override
    public Optional<Device> handle(GetDeviceByIdQuery query) {
        return repository.findById(query.id());
    }

    @Override
    public List<Device> handle(GetAllDevicesByFarmerIdQuery query) {
        return repository.findAllByFarmerUser_Id(query.farmerId());
    }
}
