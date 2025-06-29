package com.growwithme.crops.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.crops.domain.model.aggregates.CropActivity;
import com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import com.growwithme.crops.domain.model.commands.UpdateCropActivityCommand;
import com.growwithme.crops.domain.services.CropActivityCommandService;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropActivityRepository;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CropActivityCommandServiceImpl implements CropActivityCommandService {
    private final CropActivityRepository cropActivityRepository;
    private final CropRepository cropRepository;

    @Override
    public Optional<CropActivity> handle(CreateCropActivityCommand command) {
        var cropResult = cropRepository.findById(command.cropId());

        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Crop with ID " + command.cropId() + " does not exist.");
        }

        var existingCropActivity = cropActivityRepository.existsCropActivityByCrop_IdAndActivityDateNot(cropResult.get().getId(), command.activityDate());

        if (existingCropActivity) {
            throw new IllegalArgumentException("Crop activity with the same date already exists for this crop.");
        }

        var crop = cropResult.get();

        var newCropActivity = new CropActivity(
                crop,
                command.activityDate(),
                command.description()
        );

        try {
            var savedCropActivity = cropActivityRepository.save(newCropActivity);
            return Optional.of(savedCropActivity);
        } catch (Exception e) {
            throw new RuntimeException("Error creating crop activity: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteCropActivityCommand command) {
        try {
            var cropActivityResult = cropActivityRepository.findById(command.id());

            if (cropActivityResult.isEmpty()) {
                throw new IllegalArgumentException("Crop activity not found with ID: " + command.id());
            }

            cropActivityRepository.deleteById(command.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting crop activity: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteAllCropActivitiesByCropIdCommand command) {
        try {
            var cropResult = cropRepository.findById(command.cropId());

            if (cropResult.isEmpty()) {
                throw new IllegalArgumentException("Crop not found with ID: " + command.cropId());
            }

            cropActivityRepository.deleteAllByCrop_Id(command.cropId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting all crop activities for crop ID: " + command.cropId(), e);
        }
    }

    @Override
    public Optional<CropActivity> handle(UpdateCropActivityCommand command) {
        var cropActivityResult = cropActivityRepository.findById(command.id());

        if (cropActivityResult.isEmpty()) {
            throw new IllegalArgumentException("Crop activity not found with ID: " + command.id());
        }

        var cropActivity = cropActivityResult.get();

        var cropResult = cropRepository.findById(cropActivityResult.get().getCrop().getId());

        if (cropResult.isEmpty()) {
            throw new IllegalArgumentException("Crop not found with ID: " + cropActivityResult.get().getCrop().getId());
        }

        var crop = cropResult.get();

        var existingCropActivity = cropActivityRepository.existsCropActivityByIdAndCrop_IdNot(command.id(), crop.getId());
        var existingCropActivityWithSameDate = cropActivityRepository.existsCropActivityByCrop_IdAndActivityDateNot(crop.getId(), command.activityDate());

        if (existingCropActivity) {
            throw new IllegalArgumentException("Crop activity does not belong to the specified crop.");
        }
        if (existingCropActivityWithSameDate) {
            throw new IllegalArgumentException("Crop activity with the same date already exists for this crop.");
        }

        cropActivity.setActivityDate(command.activityDate());
        cropActivity.setDescription(command.description());

        try {
            var savedCropActivity = cropActivityRepository.save(cropActivity);
            return Optional.of(savedCropActivity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating crop activity: " + e.getMessage(), e);
        }
    }
}
