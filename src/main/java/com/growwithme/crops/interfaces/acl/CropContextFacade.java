package com.growwithme.crops.interfaces.acl;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.domain.model.valueobjects.CropCategory;
import com.growwithme.crops.domain.model.valueobjects.CropStatus;
import java.util.List;

public interface CropContextFacade {
    Long createCrop(Long farmerId, String productName, String code, CropCategory category, CropStatus status, Float area, String location, Float cost);

    List<Crop> fetchAllCropsByFarmerId(Long farmerId);
}
