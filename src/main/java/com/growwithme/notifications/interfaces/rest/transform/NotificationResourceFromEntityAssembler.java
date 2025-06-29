package com.growwithme.notifications.interfaces.rest.transform;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification entity) {
        return new NotificationResource(
                entity.getId(),
                entity.getFarmerUser().getId(),
                entity.getTitle(),
                entity.getMessage()
        );
    }
}
