package com.growwithme.consultations.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ConsultationStatus {
    PENDING,
    ACCEPTED,
    COMPLETED,
}
