package java.com.growwithme.crops.application.commandservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.com.growwithme.crops.domain.model.commands.CreateCropCommand;
import java.com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import java.com.growwithme.crops.domain.services.CropCommandService;
import java.com.growwithme.crops.infrastructure.persistence.jpa.repositories.CropRepository;
import java.util.Optional;

@Service
public class CropCommandServiceImpl implements CropCommandService {
    private final CropRepository repository;

    public CropCommandServiceImpl(CropRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Crop> handle(CreateCropCommand command) {
        var crop = new Crop(command);
        var createdCrop = repository.save(crop);
        return Optional.of(createdCrop);
    }

    @Override
    public void handle(DeleteCropCommand command) {
        repository.deleteById(command.id());
    }
}
