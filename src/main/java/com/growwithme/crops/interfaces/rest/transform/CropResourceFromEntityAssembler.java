package com.growwithme.crops.interfaces.rest.transform;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.interfaces.rest.resources.CropResource;

public class CropResourceFromEntityAssembler {
    public static CropResource toResourceFromEntity(Crop entity) {
        return new CropResource(
                entity.getId(),
                entity.getFarmerUser().getId(),
                entity.getProductName(),
                entity.getCode(),
                entity.getCategory(),
                entity.getStatus(),
                entity.getArea(),
                entity.getLocation()
        );
    }
}
