package java.com.growwithme.crops.domain.services;

import java.com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import java.com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import java.com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import java.com.growwithme.crops.domain.model.commands.UpdateCropActivityCommand;
import java.util.Optional;

public interface CropActivityCommandService {
    Optional<CropActivity> handle(CreateCropActivityCommand command);
    void handle(DeleteCropActivityCommand command);
    void handle(DeleteAllCropActivitiesByCropIdCommand command);
    Optional<CropActivity> handle(UpdateCropActivityCommand command);
}
