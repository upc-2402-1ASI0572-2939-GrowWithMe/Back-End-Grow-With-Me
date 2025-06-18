package com.growwithme.crops.domain.services;

import com.growwithme.crops.domain.model.aggregates.CropActivity;
import com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import com.growwithme.crops.domain.model.commands.UpdateCropActivityCommand;
import java.util.Optional;

public interface CropActivityCommandService {
    Optional<CropActivity> handle(CreateCropActivityCommand command);
    void handle(DeleteCropActivityCommand command);
    void handle(DeleteAllCropActivitiesByCropIdCommand command);
    Optional<CropActivity> handle(UpdateCropActivityCommand command);
}
