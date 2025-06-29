package com.growwithme.notifications.interfaces.rest.transform;

import com.growwithme.notifications.domain.model.commands.CreateNotificationCommand;
import com.growwithme.notifications.interfaces.rest.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {
    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(
                resource.farmerId(),
                resource.title(),
                resource.message()
        );
    }
}
