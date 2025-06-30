package com.growwithme.crops.interfaces.rest.resources;

import com.growwithme.crops.domain.model.valueobjects.CropCategory;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;

public record CreateCropResource(
        Long farmerId,
        String productName,
        String code,
        CropCategory category,
        Float area,
        String location
) {
}
