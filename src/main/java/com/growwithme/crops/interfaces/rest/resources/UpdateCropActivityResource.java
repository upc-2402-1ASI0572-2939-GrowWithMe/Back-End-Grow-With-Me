package com.growwithme.crops.interfaces.rest.resources;

import java.util.Date;

public record UpdateCropActivityResource(
        Date activityDate,
        String description
) {
}
