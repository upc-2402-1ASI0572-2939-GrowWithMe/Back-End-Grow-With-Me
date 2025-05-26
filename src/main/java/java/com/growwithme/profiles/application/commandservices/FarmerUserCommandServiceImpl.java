package java.com.growwithme.profiles.application.commandservices;

import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import java.com.growwithme.profiles.domain.model.commands.farmer.DeleteFarmerUserCommand;
import java.com.growwithme.profiles.domain.services.FarmerUserCommandService;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.FarmerUserRepository;
import java.util.Optional;

@Service
public class FarmerUserCommandServiceImpl implements FarmerUserCommandService {
    private final FarmerUserRepository repository;

    public FarmerUserCommandServiceImpl(FarmerUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<FarmerUser> handle(CreateFarmerUserCommand command) {
        var farmer = new FarmerUser(command);
        var createdFarmer = repository.save(farmer);
        return Optional.of(createdFarmer);
    }

    @Override
    public void handle(DeleteFarmerUserCommand command) {
        repository.deleteById(command.id());
    }
}
