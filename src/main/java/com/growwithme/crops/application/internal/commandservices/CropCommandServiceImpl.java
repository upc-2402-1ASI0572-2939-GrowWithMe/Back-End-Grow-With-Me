package com.growwithme.crops.application.internal.commandservices;

import com.growwithme.crops.domain.model.commands.CreateCropCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import com.growwithme.crops.domain.model.commands.UpdateCropCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import com.growwithme.crops.domain.model.aggregates.Crop;

import com.growwithme.crops.domain.services.CropCommandService;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CropCommandServiceImpl implements CropCommandService {
    private final CropRepository repository;
    private final ExternalIamService externalIamService;

    @Override
    public Optional<Crop> handle(CreateCropCommand command) {
        var farmerUserResult = externalIamService.fetchFarmerUserById(command.farmerId());

        if (farmerUserResult.isEmpty()) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + command.farmerId());
        }

        var existingCrop = repository.existsCropByProductNameAndCodeAndFarmerUser_IdNot(command.productName(), command.code(), farmerUserResult.get().getId());

        if (existingCrop) {
            throw new IllegalArgumentException("Crop with the same product name and code already exists for this farmer.");
        }

        var newCrop = new Crop(
                farmerUserResult.get(),
                command.productName(),
                command.code(),
                command.category(),
                command.area(),
                command.location(),
                command.cost()
        );

        try {
            var savedCrop = repository.save(newCrop);
            return Optional.of(savedCrop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating crop: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteCropCommand command) {
        try {
            var cropResult = repository.findById(command.id());

            if (cropResult.isEmpty()) {
                throw new IllegalArgumentException("Crop not found with ID: " + command.id());
            }

            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting crop: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Crop> handle(UpdateCropCommand command) {
        var cropResult = repository.findById(command.id());

        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Crop not found with ID: " + command.id());
        }

        var crop = cropResult.get();

        var farmerUserResult = externalIamService.fetchFarmerUserById(crop.getFarmerUser().getId());

        if (farmerUserResult.isEmpty()) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + crop.getFarmerUser().getId());
        }

        var farmerUser = farmerUserResult.get();

        var existingCrop = repository.existsCropByIdAndFarmerUser_IdNot(command.id(), farmerUser.getId());

        if (existingCrop) {
            throw new IllegalArgumentException("Crop with the same product name and code already exists.");
        }

        crop.setProductName(command.productName());
        crop.setCode(command.code());
        crop.setCategory(command.category());
        crop.setArea(command.area());
        crop.setLocation(command.location());
        crop.setCost(command.cost());

        try {
            var savedCrop = repository.save(crop);
            return Optional.of(savedCrop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating crop: " + e.getMessage(), e);
        }
    }
}
