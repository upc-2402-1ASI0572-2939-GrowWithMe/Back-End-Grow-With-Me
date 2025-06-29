package com.growwithme.devices.application.internal.commandservices;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import com.growwithme.devices.application.internal.outboundservices.acl.ExternalCropService;
import com.growwithme.devices.domain.model.aggregates.Device;
import com.growwithme.devices.domain.model.commands.CreateDeviceCommand;
import com.growwithme.devices.domain.model.commands.DeleteDeviceCommand;
import com.growwithme.devices.domain.services.DeviceCommandService;
import com.growwithme.devices.infrastructure.persistence.jpa.repositories.DeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeviceCommandServiceImpl implements DeviceCommandService {
    private final DeviceRepository repository;
    private final ExternalIamService externalIamService;
    private final ExternalCropService externalCropService;

    @Override
    public Optional<Device> handle(CreateDeviceCommand command) {
        var farmerUserResult = externalIamService.fetchFarmerUserById(command.farmerId());

        if (farmerUserResult.isEmpty()) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + command.farmerId());
        }

        var cropResult = externalCropService.fetchCropById(command.cropId());

        if (cropResult == null) {
            throw new IllegalArgumentException("Crop not found with ID: " + command.cropId());
        }

        var farmerUser = farmerUserResult.get();

        var existingDevice = repository.existsDeviceByNameAndCrop_IdAndFarmerUser_IdNot(command.name(), command.cropId(), farmerUserResult.get().getId());

        if (existingDevice) {
            throw new IllegalArgumentException("Device with name '" + command.name() + "' already exists for this crop and farmer user.");
        }

        var newDevice = new Device(
                cropResult,
                farmerUserResult.get(),
                command.name(),
                command.deviceType()
        );

        try {
            var savedDevice = repository.save(newDevice);
            return Optional.of(savedDevice);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating device: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteDeviceCommand command) {
        try {
            var deviceResult = repository.findById(command.id());

            if (deviceResult.isEmpty()) {
                throw new IllegalArgumentException("Device not found with ID: " + command.id());
            }

            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting device: " + e.getMessage(), e);
        }
    }
}
