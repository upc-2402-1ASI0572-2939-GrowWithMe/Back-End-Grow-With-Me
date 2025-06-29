package com.growwithme.consultations.interfaces.rest.resources;

import com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;

public record ConsultationResource(
        Long id,
        Long farmerId,
        String title,
        String description
) {
}
