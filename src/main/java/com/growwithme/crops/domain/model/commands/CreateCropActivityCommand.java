package com.growwithme.crops.domain.model.commands;

import java.time.LocalDate;
import java.util.Date;

public record CreateCropActivityCommand(Long cropId, LocalDate activityDate, String description) {
}
