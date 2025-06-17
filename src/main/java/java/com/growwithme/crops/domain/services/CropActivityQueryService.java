package java.com.growwithme.crops.domain.services;

import java.com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.com.growwithme.crops.domain.model.queries.GetAllCropActivitiesByCropIdQuery;
import java.util.List;

public interface CropActivityQueryService {
    List<CropActivity> handle(GetAllCropActivitiesByCropIdQuery query);
}
