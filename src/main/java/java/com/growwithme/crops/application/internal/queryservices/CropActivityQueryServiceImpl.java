package java.com.growwithme.crops.application.internal.queryservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.com.growwithme.crops.domain.model.queries.GetAllCropActivitiesByCropIdQuery;
import java.com.growwithme.crops.domain.services.CropActivityQueryService;
import java.com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropActivityRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class CropActivityQueryServiceImpl implements CropActivityQueryService {
    private final CropActivityRepository repository;

    @Override
    public List<CropActivity> handle(GetAllCropActivitiesByCropIdQuery query) {
        return repository.findAllByCrop_Id(query.cropId());
    }
}
