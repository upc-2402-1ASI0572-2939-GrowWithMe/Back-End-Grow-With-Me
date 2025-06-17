package java.com.growwithme.crops.interfaces.rest.transform;

import java.com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.com.growwithme.crops.domain.model.commands.CreateCropActivityCommand;
import java.com.growwithme.crops.interfaces.rest.resources.CreateCropActivityResource;
import java.com.growwithme.crops.interfaces.rest.resources.CropActivityResource;

public class CropActivityResourceFromEntityAssembler {
    public static CropActivityResource toResourceFromEntity(CropActivity entity) {
        return new CropActivityResource(
                entity.getId(),
                entity.getCrop().getId(),
                entity.getActivityDate(),
                entity.getDescription()
        );
    }
}
