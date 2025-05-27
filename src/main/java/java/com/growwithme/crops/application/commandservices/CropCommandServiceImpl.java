package java.com.growwithme.crops.application.commandservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.com.growwithme.crops.domain.model.commands.CreateCropCommand;
import java.com.growwithme.crops.domain.model.commands.DeleteCropCommand;
import java.com.growwithme.crops.domain.model.commands.UpdateCropCommand;
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
        if (repository.existsByCode(command.code())) {
            throw new IllegalArgumentException("Crop with code " + command.code() + " already exists.");
        }
        var crop = new Crop(command);
        try {
            repository.save(crop);
            return Optional.of(crop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while creating crop: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteCropCommand command) {
        if (!repository.existsById(command.id())) {
            throw new IllegalArgumentException("Crop with ID " + command.id() + " doesn't exist.");
        }
        try {
            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting crop: " + e.getMessage());
        }
    }

    @Override
    public Optional<Crop> handle(UpdateCropCommand command) {
        if (!repository.existsById(command.id())) {
            throw new IllegalArgumentException("Crop with ID " + command.id() + " doesn't exist.");
        }
        var result = repository.findById(command.id());
        var cropToUpdate = result.get();
        try {
            var updatedCrop = repository.save(cropToUpdate.update(command.name(), command.code(), command.category(), command.area(), command.location(), command.status(), command.cost(), command.profitReturn()));
            return Optional.of(updatedCrop);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating crop:" + e.getMessage());
        }
    }

}
