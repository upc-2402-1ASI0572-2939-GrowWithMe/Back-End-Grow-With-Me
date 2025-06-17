package java.com.growwithme.crops.interfaces.rest.resources;

import java.com.growwithme.crops.domain.model.valueobjects.CropCategory;
import java.com.growwithme.crops.domain.model.valueobjects.CropStatus;

public record CreateCropResource(
        Long farmerId,
        String productName,
        String code,
        CropCategory category,
        CropStatus status,
        Float area,
        String location,
        Float cost
) {
}
