package java.com.growwithme.consultations.interfaces.acl;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;
import java.util.List;
import java.util.Optional;

public interface ConsultationContextFacade {
    Long createConsultation(Long farmerId, String title, String description, ConsultationStatus status);
    List<Consultation> fetchAllConsultations();
    List<Consultation> fetchAllConsultationsByFarmerId(Long farmerId);
}
