package java.com.growwithme.consultations.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import java.com.growwithme.consultations.domain.services.ConsultationCommandService;
import java.com.growwithme.consultations.infrastructure.persistence.jpa.repositories.ConsultationRepository;
import java.com.growwithme.crops.application.internal.outboundservices.acl.ExternalFarmerUserService;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultationCommandServiceImpl implements ConsultationCommandService {
    private final ConsultationRepository repository;
    private final ExternalFarmerUserService externalFarmerUserService;

    @Override
    public Optional<Consultation> handle(CreateConsultationCommand command) {
        var farmerUserResult = externalFarmerUserService.fetchFarmerUserById(command.farmerId());

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
}
