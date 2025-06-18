package java.com.growwithme.crops.domain.model.commands;

import java.com.growwithme.crops.domain.model.valueobjects.CropCategory;

public record UpdateCropCommand(Long id, Long farmerId, String productName, String code, CropCategory category, Float area, String location, Float cost) {
}
