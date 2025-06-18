package com.growwithme.crops.interfaces.acl;

import com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.util.List;

public interface CropActivityContextFacade {
    void deleteCropActivity(Long id);

    void deleteAllCropActivitiesByCropId(Long cropId);

    List<CropActivity> fetchAllCropActivitiesByCropId(Long cropId);
}
