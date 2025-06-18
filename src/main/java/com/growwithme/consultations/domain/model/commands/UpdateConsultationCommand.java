package com.growwithme.consultations.domain.model.commands;

public record UpdateConsultationCommand(Long id, String title, String description) {
}
