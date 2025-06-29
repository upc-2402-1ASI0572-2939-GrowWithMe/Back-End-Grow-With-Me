package com.growwithme.iam.application.internal.outboundservices.acl;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.interfaces.acl.NotificationContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExternalNotificationService {
    private final NotificationContextFacade notificationContextFacade;

    public Optional<Notification> createNotification(Long farmerId, String title, String message) {
        return notificationContextFacade.createNotification(farmerId, title, message);
    }
}
