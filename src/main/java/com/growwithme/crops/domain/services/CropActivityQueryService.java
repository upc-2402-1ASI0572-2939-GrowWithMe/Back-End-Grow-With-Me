package com.growwithme.crops.domain.services;

import com.growwithme.crops.domain.model.aggregates.CropActivity;
import com.growwithme.crops.domain.model.queries.GetAllCropActivitiesByCropIdQuery;
import java.util.List;

public interface CropActivityQueryService {
    List<CropActivity> handle(GetAllCropActivitiesByCropIdQuery query);
}
