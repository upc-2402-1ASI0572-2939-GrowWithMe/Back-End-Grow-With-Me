package com.growwithme.consultations.interfaces.rest.transform;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import com.growwithme.consultations.interfaces.rest.resources.ConsultationResource;

public class ConsultationResourceFromEntityAssembler {
    public static ConsultationResource toResourceFromEntity(Consultation entity) {
        return new ConsultationResource(
                entity.getId(),
                entity.getFarmerUser().getId(),
                entity.getTitle(),
                entity.getDescription()
        );
    }
}
