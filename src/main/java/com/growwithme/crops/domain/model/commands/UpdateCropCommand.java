package com.growwithme.crops.domain.model.commands;

import com.growwithme.crops.domain.model.valueobjects.CropCategory;

public record UpdateCropCommand(Long id, String productName, String code, CropCategory category, Float area, String location) {
}
