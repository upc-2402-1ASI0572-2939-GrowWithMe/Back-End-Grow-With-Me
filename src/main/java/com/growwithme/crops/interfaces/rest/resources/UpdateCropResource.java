package com.growwithme.crops.interfaces.rest.resources;

import com.growwithme.crops.domain.model.valueobjects.CropCategory;

public record UpdateCropResource(
        String productName,
        String code,
        CropCategory category,
        Float area,
        String location,
        Float cost
) {
}
