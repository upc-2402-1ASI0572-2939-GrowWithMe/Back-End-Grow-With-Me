package java.com.growwithme.profiles.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.profiles.domain.model.aggregates.ConsultantUser;
import java.com.growwithme.profiles.domain.model.commands.consultant.CreateConsultantUserCommand;
import java.com.growwithme.profiles.domain.model.commands.consultant.DeleteConsultantUserCommand;
import java.com.growwithme.profiles.domain.model.commands.consultant.UpdateConsultantUserCommand;
import java.com.growwithme.profiles.domain.services.ConsultantUserCommandService;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.ConsultantUserRepository;
import java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories.FarmerUserRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultantUserCommandServiceImpl implements ConsultantUserCommandService {
    private final ConsultantUserRepository consultantUserRepository;
    private final FarmerUserRepository farmerUserRepository;

    @Override
    public Optional<ConsultantUser> handle(CreateConsultantUserCommand command) {
        var existingConsultantUser = consultantUserRepository.existsConsultantUserByDni(command.dni());

        if (existingConsultantUser) {
            throw new IllegalArgumentException("Consultant user with the same DNI already exists.");
        }

        var existingFarmerUser = farmerUserRepository.existsFarmerUserByDni(command.dni());

        if (existingFarmerUser) {
            throw new IllegalArgumentException("A farmer user with the same DNI already exists. Please use a different DNI.");
        }

        var newConsultantUser = new ConsultantUser(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone(),
                command.photoUrl(),
                command.dni()
        );

        try {
            var savedConsultantUser = consultantUserRepository.save(newConsultantUser);
            return Optional.of(savedConsultantUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating consultant user: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteConsultantUserCommand command) {
        try {
            var consultantUserResult = consultantUserRepository.findById(command.id());

            if (consultantUserResult.isEmpty()) {
                throw new IllegalArgumentException("Consultant user not found.");
            }

            consultantUserRepository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting consultant user: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ConsultantUser> handle(UpdateConsultantUserCommand command) {
        var consultantUserResult = consultantUserRepository.findById(command.id());

        var consultantUser = consultantUserResult.get();

        if (consultantUserResult.isEmpty()) {
            throw new IllegalArgumentException("Consultant user not found with ID: " + command.id());
        }

        try {
            var existingConsultantUser = consultantUserRepository.existsConsultantUserByPhone(command.phone());

            if (existingConsultantUser) {
                throw new IllegalArgumentException("Consultant user with the same phone number already exists.");
            }

            consultantUser.setPhone(command.phone());
            consultantUser.setPhotoUrl(command.photoUrl());

            var savedConsultantUser = consultantUserRepository.save(consultantUser);
            return Optional.of(savedConsultantUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating consultant user: " + e.getMessage(), e);
        }
    }
}
