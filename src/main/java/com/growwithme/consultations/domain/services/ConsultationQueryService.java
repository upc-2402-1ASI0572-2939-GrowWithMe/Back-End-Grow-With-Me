package com.growwithme.consultations.domain.services;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import com.growwithme.consultations.domain.model.queries.GetAllConsultationsByFarmerIdQuery;
import com.growwithme.consultations.domain.model.queries.GetAllConsultationsQuery;
import java.util.List;

public interface ConsultationQueryService {
    List<Consultation> handle(GetAllConsultationsQuery query);
    List<Consultation> handle(GetAllConsultationsByFarmerIdQuery query);
}
