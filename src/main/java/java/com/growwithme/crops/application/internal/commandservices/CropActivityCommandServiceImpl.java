package java.com.growwithme.crops.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import java.com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import java.com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import java.com.growwithme.crops.domain.model.commands.UpdateCropActivityCommand;
import java.com.growwithme.crops.domain.services.CropActivityCommandService;
import java.com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropActivityRepository;
import java.com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
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

        try {
            var existingCropActivityWithSameDate = cropActivityRepository.existsCropActivityByCrop_IdAndActivityDate(cropActivity.getCrop().getId(), command.activityDate());

            if (existingCropActivityWithSameDate) {
                throw new IllegalArgumentException("Crop activity with the same date already exists for this crop.");
            }

            cropActivity.setActivityDate(command.activityDate());
            cropActivity.setDescription(command.description());

            var savedCropActivity = cropActivityRepository.save(cropActivity);
            return Optional.of(savedCropActivity);
        } catch (Exception e) {
            throw new RuntimeException("Error updating crop activity: " + e.getMessage(), e);
        }
    }
}
