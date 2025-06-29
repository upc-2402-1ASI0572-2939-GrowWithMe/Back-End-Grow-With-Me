package com.growwithme.crops.domain.model.commands;

import com.growwithme.crops.domain.model.valueobjects.CropCategory;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;

public record CreateCropCommand(Long farmerId, String productName, String code, CropCategory category, Float area, String location) {
}
