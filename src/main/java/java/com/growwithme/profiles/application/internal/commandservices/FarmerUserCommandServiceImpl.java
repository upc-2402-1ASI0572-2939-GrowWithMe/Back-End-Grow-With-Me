package java.com.growwithme.profiles.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;
import java.com.growwithme.profiles.domain.model.commands.farmer.CreateFarmerUserCommand;
import java.com.growwithme.profiles.domain.model.commands.farmer.DeleteFarmerUserCommand;
import java.com.growwithme.profiles.domain.model.commands.farmer.UpdateFarmerUserCommand;
import java.com.growwithme.profiles.domain.services.FarmerUserCommandService;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.ConsultantUserRepository;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.FarmerUserRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FarmerUserCommandServiceImpl implements FarmerUserCommandService {
    private final FarmerUserRepository farmerUserRepository;
    private final ConsultantUserRepository consultantUserRepository;

    @Override
    public Optional<FarmerUser> handle(CreateFarmerUserCommand command) {
        var existingUser = farmerUserRepository.existsFarmerUserByDni(command.dni());

        if (existingUser) {
            throw new IllegalArgumentException("Farmer user with the same DNI already exists.");
        }

        var existingConsultantUser = consultantUserRepository.existsConsultantUserByDni(command.dni());

        if (existingConsultantUser) {
            throw new IllegalArgumentException("A consultant user with the same DNI already exists. Please use a different DNI.");
        }

        var newFarmerUser = new FarmerUser(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone(),
                command.photoUrl(),
                command.dni()
        );

        try {
            var savedFarmerUser = farmerUserRepository.save(newFarmerUser);
            return Optional.of(savedFarmerUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create farmer user: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteFarmerUserCommand command) {
        try {
            var farmerUserResult = farmerUserRepository.findById(command.id());

            if (farmerUserResult.isEmpty()) {
                throw new IllegalArgumentException("Farmer user not found with ID: " + command.id());
            }

            farmerUserRepository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting farmer user: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<FarmerUser> handle(UpdateFarmerUserCommand command) {
        var farmerUserResult = farmerUserRepository.findById(command.id());

        var farmerUser = farmerUserResult.get();

        if (farmerUserResult.isEmpty()) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + command.id());
        }

        try {
            var existingFarmerUser = farmerUserRepository.existsFarmerUserByPhone(farmerUser.getPhone());

            if (existingFarmerUser) {
                throw new IllegalArgumentException("Farmer user with the same phone number already exists.");
            }

            farmerUser.setPhone(command.phone());
            farmerUser.setPhotoUrl(command.photoUrl());

            var savedFarmerUser = farmerUserRepository.save(farmerUser);
            return Optional.of(savedFarmerUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating farmer user: " + e.getMessage());
        }
    }
}
