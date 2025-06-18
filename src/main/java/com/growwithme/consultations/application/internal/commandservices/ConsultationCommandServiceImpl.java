package com.growwithme.consultations.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import com.growwithme.consultations.domain.model.commands.DeleteConsultationCommand;
import com.growwithme.consultations.domain.model.commands.UpdateConsultationCommand;
import com.growwithme.consultations.domain.services.ConsultationCommandService;
import com.growwithme.consultations.infrastructure.persistence.jpa.repositories.ConsultationRepository;
import com.growwithme.crops.application.internal.outboundservices.acl.ExternalFarmerUserService;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultationCommandServiceImpl implements ConsultationCommandService {
    private final ConsultationRepository repository;
    private final ExternalFarmerUserService externalFarmerUserService;

    @Override
    public Optional<Consultation> handle(CreateConsultationCommand command) {
        var farmerUserResult = externalFarmerUserService.fetchFarmerUserById(command.farmerId());

        if (farmerUserResult == null) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + command.farmerId());
        }

        var existingConsultation = repository.existsConsultationByTitleAndFarmerUser_Id(command.title(), farmerUserResult.getId());

        if (existingConsultation) {
            throw new IllegalArgumentException("Consultation with the same title already exists for this farmer user.");
        }

        var newConsultation = new Consultation(
                farmerUserResult,
                command.title(),
                command.description(),
                command.status()
        );

        try {
            var savedConsultation = repository.save(newConsultation);
            return Optional.of(savedConsultation);
        } catch (Exception e) {
            throw new RuntimeException("Error creating consultation: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Consultation> handle(UpdateConsultationCommand command) {
        var consultationResult = repository.findById(command.id());
        var farmerUserResult = externalFarmerUserService.fetchFarmerUserById(consultationResult.get().getFarmerUser().getId());

        if (consultationResult.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + command.id());
        }

        if (farmerUserResult == null) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + farmerUserResult.getId());
        }

        var consultation = consultationResult.get();

        try {
            var existingConsultation = repository.existsConsultationByIdAndFarmerUser_Id(command.id(), farmerUserResult.getId());

            if (existingConsultation) {
                throw new IllegalArgumentException("Consultation does not belong to the specified farmer user.");
            }

            var existingConsultationByTitle = repository.findByTitleAndFarmerUser_Id(command.title(), farmerUserResult.getId());

            if (existingConsultationByTitle) {
                throw new IllegalArgumentException("Consultation with the same title already exists for this farmer user.");
            }

            consultation.setTitle(command.title());
            consultation.setDescription(command.description());

            var savedConsultation = repository.save(consultation);
            return Optional.of(savedConsultation);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating consultation: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteConsultationCommand command) {
        try    {
            var consultationResult = repository.findById(command.id());

            if (consultationResult.isEmpty()) {
                throw new IllegalArgumentException("Consultation not found with ID: " + command.id());
            }

            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting consultation: " + e.getMessage(), e);
        }
    }
}
