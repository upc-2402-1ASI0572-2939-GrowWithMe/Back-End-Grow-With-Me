package com.growwithme.crops.domain.model.commands;

import com.growwithme.crops.domain.model.valueobjects.CropCategory;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;

public record CreateCropCommand(Long farmerId, String productName, String code, CropCategory category, CropStatus status, Float area, String location, Float cost) {
}
