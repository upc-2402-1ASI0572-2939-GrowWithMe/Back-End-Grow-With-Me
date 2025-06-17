package java.com.growwithme.consultations.application.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.com.growwithme.consultations.domain.model.commands.CreateConsultationCommand;
import java.com.growwithme.consultations.domain.model.queries.GetAllConsultationsByFarmerIdQuery;
import java.com.growwithme.consultations.domain.model.queries.GetAllConsultationsQuery;
import java.com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;
import java.com.growwithme.consultations.domain.services.ConsultationCommandService;
import java.com.growwithme.consultations.domain.services.ConsultationQueryService;
import java.com.growwithme.consultations.interfaces.acl.ConsultationContextFacade;
import java.util.List;

@Service
@AllArgsConstructor
public class ConsultationContextFacadeImpl implements ConsultationContextFacade {
    private final ConsultationCommandService commandService;
    private final ConsultationQueryService queryService;

    @Override
    public Long createConsultation(Long farmerId, String title, String description, ConsultationStatus status) {
        var consultationResult = commandService.handle(new CreateConsultationCommand(farmerId, title, description, status));

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
