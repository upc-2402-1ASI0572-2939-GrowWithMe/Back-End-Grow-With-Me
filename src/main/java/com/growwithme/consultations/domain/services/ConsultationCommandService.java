package com.growwithme.consultations.domain.services;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import com.growwithme.consultations.domain.model.commands.DeleteConsultationCommand;
import com.growwithme.consultations.domain.model.commands.UpdateConsultationCommand;
import java.util.Optional;

public interface ConsultationCommandService {
    Optional<Consultation> handle(CreateConsultationCommand command);
    Optional<Consultation> handle(UpdateConsultationCommand command);
    void handle(DeleteConsultationCommand command);
}
