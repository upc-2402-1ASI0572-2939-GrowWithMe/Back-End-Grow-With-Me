package com.growwithme.crops.domain.services;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.domain.model.commands.CreateCropCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import com.growwithme.crops.domain.model.commands.UpdateCropCommand;

import java.util.Optional;

public interface CropCommandService {
    Optional<Crop> handle(CreateCropCommand command);
    void handle(DeleteCropCommand command);
    Optional<Crop> handle(UpdateCropCommand command);
}