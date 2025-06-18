package com.growwithme.consultations.interfaces.acl;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;
import java.util.List;

public interface ConsultationContextFacade {
    Long createConsultation(Long farmerId, String title, String description, ConsultationStatus status);
    List<Consultation> fetchAllConsultations();
    List<Consultation> fetchAllConsultationsByFarmerId(Long farmerId);
}
