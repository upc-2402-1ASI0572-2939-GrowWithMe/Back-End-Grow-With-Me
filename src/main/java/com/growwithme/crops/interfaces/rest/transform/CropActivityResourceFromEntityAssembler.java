package com.growwithme.crops.interfaces.rest.transform;

import com.growwithme.crops.domain.model.aggregates.CropActivity;
import com.growwithme.crops.interfaces.rest.resources.CropActivityResource;

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
