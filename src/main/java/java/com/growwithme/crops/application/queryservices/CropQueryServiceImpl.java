package java.com.growwithme.crops.application.queryservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.com.growwithme.crops.domain.model.queries.GetAllCropsQuery;
import java.com.growwithme.crops.domain.model.queries.GetCropByIdQuery;
import java.com.growwithme.crops.domain.services.CropQueryService;
import java.com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
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
    public Optional<Crop> handle(GetCropByIdQuery query) {
        return repository.findById(query.id());
    }
}
