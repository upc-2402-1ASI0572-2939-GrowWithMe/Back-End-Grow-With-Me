package com.growwithme.consultations.interfaces.rest.resources;

import com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;

public record CreateConsultationResource(
        Long farmerId,
        String title,
        String description
) {
}
