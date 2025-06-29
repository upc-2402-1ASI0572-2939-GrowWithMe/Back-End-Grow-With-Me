package com.growwithme.notifications.interfaces.acl;

import com.growwithme.notifications.domain.model.aggregates.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationContextFacade {
    Optional<Notification> createNotification(Long farmerId, String title, String message);

    List<Notification> fetchAllNotificationsByFarmerId(Long farmerId);

    void deleteNotification(Long notificationId);
}
