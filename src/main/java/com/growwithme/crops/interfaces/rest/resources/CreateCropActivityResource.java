package com.growwithme.crops.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateCropActivityResource(
        Long cropId,
        LocalDate activityDate,
        String description
) {
}
