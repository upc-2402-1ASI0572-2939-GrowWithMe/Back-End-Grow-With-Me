package com.growwithme.consultations.application.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import com.growwithme.consultations.domain.model.queries.GetAllConsultationsByFarmerIdQuery;
import com.growwithme.consultations.domain.model.queries.GetAllConsultationsQuery;
import com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;
import com.growwithme.consultations.domain.services.ConsultationCommandService;
import com.growwithme.consultations.domain.services.ConsultationQueryService;
import com.growwithme.consultations.interfaces.acl.ConsultationContextFacade;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsultationContextFacadeImpl implements ConsultationContextFacade {
    private final ConsultationCommandService commandService;
    private final ConsultationQueryService queryService;

    @Override
    public Long createConsultation(Long farmerId, String title, String description) {
        var consultationResult = commandService.handle(new CreateConsultationCommand(farmerId, title, description));

        if (consultationResult.isEmpty()) {
            throw new IllegalStateException("Failed to create consultation");
        }

        return consultationResult.get().getId();
    }

    @Override
    public List<Consultation> fetchAllConsultations() {
        return queryService.handle(new GetAllConsultationsQuery());
    }

    @Override
    public List<Consultation> fetchAllConsultationsByFarmerId(Long farmerId) {
        return queryService.handle(new GetAllConsultationsByFarmerIdQuery(farmerId));
    }
}
