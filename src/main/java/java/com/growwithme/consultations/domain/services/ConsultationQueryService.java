package java.com.growwithme.consultations.domain.services;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.com.growwithme.consultations.domain.model.queries.GetAllConsultationsByFarmerIdQuery;
import java.com.growwithme.consultations.domain.model.queries.GetAllConsultationsQuery;
import java.util.List;

public interface ConsultationQueryService {
    List<Consultation> handle(GetAllConsultationsQuery query);
    List<Consultation> handle(GetAllConsultationsByFarmerIdQuery query);
}
