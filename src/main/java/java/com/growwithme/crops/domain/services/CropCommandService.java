package java.com.growwithme.crops.domain.services;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.com.growwithme.crops.domain.model.commands.*;
import java.util.Optional;

public interface CropCommandService {
    Optional<Crop> handle(CreateCropCommand command);
    void handle(DeleteCropCommand command);
    Optional<Crop> handle(UpdateCropCommand command);
}