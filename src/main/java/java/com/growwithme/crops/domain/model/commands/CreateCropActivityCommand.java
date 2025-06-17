package java.com.growwithme.crops.domain.model.commands;

import java.util.Date;

public record CreateCropActivityCommand(Long cropId, Date activityDate, String description) {
}
