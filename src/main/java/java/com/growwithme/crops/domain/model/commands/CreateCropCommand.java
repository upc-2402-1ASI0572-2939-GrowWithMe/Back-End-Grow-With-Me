package java.com.growwithme.crops.domain.model.commands;

import java.com.growwithme.crops.domain.model.valueobjects.CropCategory;
import java.com.growwithme.crops.domain.model.valueobjects.CropStatus;

public record CreateCropCommand(Long farmerId, String productName, String code, CropCategory category, CropStatus status, Float area, String location, Float cost) {
}
