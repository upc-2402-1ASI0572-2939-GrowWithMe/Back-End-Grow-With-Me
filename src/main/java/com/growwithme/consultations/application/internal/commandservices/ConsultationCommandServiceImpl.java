package com.growwithme.consultations.application.internal.commandservices;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import com.growwithme.consultations.domain.model.commands.DeleteConsultationCommand;
import com.growwithme.consultations.domain.model.commands.UpdateConsultationCommand;
import com.growwithme.consultations.domain.services.ConsultationCommandService;
import com.growwithme.consultations.infrastructure.persistence.jpa.repositories.ConsultationRepository;
import com.growwithme.crops.application.internal.outboundservices.acl.ExternalIamService;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultationCommandServiceImpl implements ConsultationCommandService {
    private final ConsultationRepository repository;
    private final ExternalIamService externalIamService;

    @Override
    public Optional<Consultation> handle(CreateConsultationCommand command) {
        var farmerUserResult = externalIamService.fetchFarmerUserById(command.farmerId());

        if (farmerUserResult.isEmpty()) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + command.farmerId());
        }

        var existingConsultation = repository.existsConsultationByTitleAndFarmerUser_IdNot(command.title(), farmerUserResult.get().getId());

        if (existingConsultation) {
            throw new IllegalArgumentException("Consultation with the same title already exists for this farmer user.");
        }

        var newConsultation = new Consultation(
                farmerUserResult.get(),
                command.title(),
                command.description()
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

        if (consultationResult.isEmpty()) {
            throw new IllegalArgumentException("Consultation not found with ID: " + command.id());
        }

        var consultation = consultationResult.get();

        var farmerUserResult = externalIamService.fetchFarmerUserById(consultation.getFarmerUser().getId());

        if (farmerUserResult.isEmpty()) {
            throw new IllegalArgumentException("Farmer user not found with ID: " + consultation.getFarmerUser().getId());
        }

        var farmerUser = farmerUserResult.get();

        var existingConsultation = repository.existsConsultationByIdAndFarmerUser_IdNot(command.id(), farmerUser.getId());

        if (existingConsultation) {
            throw new IllegalArgumentException("Consultation does not belong to the specified farmer user.");
        }

        var existingConsultationByTitle = repository.existsConsultationByTitleAndFarmerUser_IdNot(command.title(), farmerUser.getId());

        if (existingConsultationByTitle) {
            throw new IllegalArgumentException("Consultation with the same title already exists for this farmer user.");
        }

        consultation.setTitle(command.title());
        consultation.setDescription(command.description());

        try {
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
