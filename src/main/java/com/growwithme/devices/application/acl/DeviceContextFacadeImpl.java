package com.growwithme.devices.application.acl;

import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.domain.model.commands.CreateDeviceCommand;
import com.growwithme.devices.domain.model.queries.GetAllDevicesByFarmerIdQuery;
import com.growwithme.devices.domain.model.queries.GetDeviceByIdQuery;
import com.growwithme.devices.domain.model.valueobjects.DeviceType;
import com.growwithme.devices.domain.services.DeviceCommandService;
import com.growwithme.devices.domain.services.DeviceQueryService;
import com.growwithme.devices.interfaces.acl.DeviceContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceContextFacadeImpl implements DeviceContextFacade {
    private final DeviceCommandService commandService;
    private final DeviceQueryService queryService;

    @Override
    public Long createDevice(Long cropId, Long farmerId, String name, DeviceType deviceType) {
        var deviceResult = commandService.handle(new CreateDeviceCommand(cropId, farmerId, name, deviceType));

        if (deviceResult.isEmpty()) {
            throw new IllegalStateException("Device creation failed");
        }

        return deviceResult.get().getId();
    }

    @Override
    public List<Device> fetchAllDevicesByFarmerId(Long farmerId) {
        return queryService.handle(new GetAllDevicesByFarmerIdQuery(farmerId));
    }

    @Override
    public Optional<Device> fetchDeviceById(Long id) {
        return queryService.handle(new GetDeviceByIdQuery(id));
    }
}
