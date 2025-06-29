package com.growwithme.crops.domain.model.commands;

import java.time.LocalDate;

public record UpdateCropActivityCommand(Long id, LocalDate activityDate, String description) {
}
