package com.growwithme.crops.interfaces.rest.resources;

import java.util.Date;

public record CropActivityResource(
        Long id,
        Long cropId,
        Date activityDate,
        String description
) {
}
