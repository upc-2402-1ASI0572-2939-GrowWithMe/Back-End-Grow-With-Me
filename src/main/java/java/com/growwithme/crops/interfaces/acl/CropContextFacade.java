package java.com.growwithme.crops.interfaces.acl;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.com.growwithme.crops.domain.model.valueobjects.CropCategory;
import java.com.growwithme.crops.domain.model.valueobjects.CropStatus;
import java.util.List;

public interface CropContextFacade {
    Long createCrop(Long farmerId, String productName, String code, CropCategory category, CropStatus status, Float area, String location, Float cost);

    List<Crop> fetchAllCropsByFarmerId(Long farmerId);
}
