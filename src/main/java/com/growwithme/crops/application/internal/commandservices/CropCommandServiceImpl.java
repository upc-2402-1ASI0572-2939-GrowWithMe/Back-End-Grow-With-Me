package com.growwithme.crops.application.internal.commandservices;

import com.growwithme.crops.domain.model.commands.CreateCropCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import com.growwithme.crops.domain.model.commands.UpdateCropCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalFarmerUserService;
import com.growwithme.crops.domain.model.aggregates.Crop;

import com.growwithme.crops.domain.services.CropCommandService;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CropCommandServiceImpl implements CropCommandService {
    private final CropRepository cropRepository;
    private final ExternalFarmerUserService externalFarmerUserService;


    @Override
    public Optional<Crop> handle(CreateCropCommand command) {
        var farmerUserResult = externalFarmerUserService.fetchFarmerUserById(command.farmerId());

        if (farmerUserResult == null) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + command.farmerId());
        }

        var existingCrop = cropRepository.existsCropByProductNameAndCodeAndFarmerUser_IdNot(command.productName(), command.code(), farmerUserResult.getId());

        if (existingCrop) {
            throw new IllegalArgumentException("Crop with the same product name and code already exists for this farmer.");
        }

        var newCrop = new Crop(
                farmerUserResult,
                command.productName(),
                command.code(),
                command.category(),
                command.area(),
                command.location(),
                command.cost()
        );

        try {
            var savedCrop = cropRepository.save(newCrop);
            return Optional.of(savedCrop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating crop: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteCropCommand command) {
        try {
            var cropResult = cropRepository.findById(command.id());

            if (cropResult.isEmpty()) {
                throw new IllegalArgumentException("Crop not found with ID: " + command.id());
            }

            cropRepository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting crop: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Crop> handle(UpdateCropCommand command) {
        var cropResult = cropRepository.findById(command.id());
        var farmerUserResult = externalFarmerUserService.fetchFarmerUserById(cropResult.get().getFarmerUser().getId());

        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Crop not found with ID: " + command.id());
        }

        var crop = cropResult.get();

        var existingCrop = cropRepository.existsCropByIdAndFarmerUser_IdNot(command.id(), farmerUserResult.getId());

        if (existingCrop) {
            throw new IllegalArgumentException("Crop with the same product name and code already exists.");
        }

        var existingCropByLocation = cropRepository.existsCropByLocationNot(crop.getLocation());

        if (existingCropByLocation) {
            throw new IllegalArgumentException("Crop with the same location already exists.");
        }

        crop.setProductName(command.productName());
        crop.setCode(command.code());
        crop.setCategory(command.category());
        crop.setArea(command.area());
        crop.setLocation(command.location());
        crop.setCost(command.cost());

        try {
            var savedCrop = cropRepository.save(crop);
            return Optional.of(savedCrop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating crop: " + e.getMessage(), e);
        }
    }
}
