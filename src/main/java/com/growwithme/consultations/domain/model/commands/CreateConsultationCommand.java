package com.growwithme.consultations.domain.model.commands;

import com.growwithme.consultations.domain.model.valueobjects.ConsultationStatus;

public record CreateConsultationCommand(Long farmerId, String title, String description) {
}
