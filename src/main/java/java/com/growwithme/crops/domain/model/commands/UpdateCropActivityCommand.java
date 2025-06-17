package java.com.growwithme.crops.domain.model.commands;

import java.util.Date;

public record UpdateCropActivityCommand(Long id, Date activityDate, String description) {
}
