package java.com.growwithme.crops.interfaces.rest.resources;

import java.util.Date;

public record CreateCropActivityResource(
        Long cropId,
        Date activityDate,
        String description
) {
}
