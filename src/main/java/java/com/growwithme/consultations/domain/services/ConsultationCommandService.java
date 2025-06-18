package java.com.growwithme.consultations.domain.services;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import java.com.growwithme.consultations.domain.model.commands.DeleteConsultationCommand;
import java.com.growwithme.consultations.domain.model.commands.UpdateConsultationCommand;
import java.util.Optional;

public interface ConsultationCommandService {
    Optional<Consultation> handle(CreateConsultationCommand command);
    Optional<Consultation> handle(UpdateConsultationCommand command);
    void handle(DeleteConsultationCommand command);
}
