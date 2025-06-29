package com.growwithme.crops.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateCropActivityResource(
        LocalDate activityDate,
        String description
) {
}
