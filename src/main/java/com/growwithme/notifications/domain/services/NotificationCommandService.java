package com.growwithme.notifications.domain.services;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.domain.model.commands.CreateNotificationCommand;
import com.growwithme.notifications.domain.model.commands.DeleteNotificationCommand;
import java.util.Optional;

public interface NotificationCommandService {
    Optional<Notification> handle(CreateNotificationCommand command);
    void handle(DeleteNotificationCommand command);
}
