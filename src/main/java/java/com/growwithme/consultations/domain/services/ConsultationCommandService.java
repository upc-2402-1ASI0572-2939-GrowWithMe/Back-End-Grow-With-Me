package java.com.growwithme.consultations.domain.services;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import java.util.Optional;

public interface ConsultationCommandService {
    Optional<Consultation> handle(CreateConsultationCommand command);
}
