package com.growwithme.crops.interfaces.rest.resources;

import java.time.LocalDate;

public record CropActivityResource(
        Long id,
        Long cropId,
        LocalDate activityDate,
        String description
) {
}
