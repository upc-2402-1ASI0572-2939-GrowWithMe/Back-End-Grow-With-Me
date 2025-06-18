package com.growwithme.crops.domain.services;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.domain.model.queries.GetAllCropsByFarmerIdQuery;
import com.growwithme.crops.domain.model.queries.GetAllCropsQuery;
import com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import java.util.List;
import java.util.Optional;

public interface CropQueryService {
    List<Crop> handle(GetAllCropsQuery query);
    List<Crop> handle(GetAllCropsByFarmerIdQuery query);
    Optional<Crop> handle(GetCropByIdQuery query);
}
