package java.com.growwithme.crops.domain.model.commands;

import java.util.Date;

public record UpdateCropActivityCommand(Long id, Long cropId, Date activityDate, String description) {
}
