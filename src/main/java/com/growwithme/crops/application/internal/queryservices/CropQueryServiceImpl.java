package com.growwithme.crops.application.internal.queryservices;

import org.springframework.stereotype.Service;

import com.growwithme.crops.domain.model.aggregates.Crop;
import com.growwithme.crops.domain.model.queries.GetAllCropsByFarmerIdQuery;
import com.growwithme.crops.domain.model.queries.GetAllCropsQuery;
import com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import com.growwithme.crops.domain.services.CropQueryService;
import com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CropQueryServiceImpl implements CropQueryService {
    private final CropRepository repository;

    public CropQueryServiceImpl(CropRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Crop> handle(GetAllCropsQuery query) {
        return repository.findAll();
    }

    @Override
    public List<Crop> handle(GetAllCropsByFarmerIdQuery query) {
        return repository.findAllByFarmerUser_Id(query.farmerId());
    }

    @Override
    public Optional<Crop> handle(GetCropByIdQuery query) {
        return repository.findById(query.id());
    }
}
