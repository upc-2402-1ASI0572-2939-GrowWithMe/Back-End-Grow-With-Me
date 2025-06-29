package com.growwithme.notifications.application.acl;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.domain.model.commands.CreateNotificationCommand;
import com.growwithme.notifications.domain.model.commands.DeleteNotificationCommand;
import com.growwithme.notifications.domain.model.queries.GetAllNotificationsByFarmerIdQuery;
import com.growwithme.notifications.domain.services.NotificationCommandService;
import com.growwithme.notifications.domain.services.NotificationQueryService;
import com.growwithme.notifications.interfaces.acl.NotificationContextFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationContextFacadeImpl implements NotificationContextFacade {
    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    @Override
    public Optional<Notification> createNotification(Long farmerId, String title, String message) {
        var notificationResult = commandService.handle(new CreateNotificationCommand(farmerId, title, message));

        if (notificationResult.isEmpty()) {
            throw new IllegalArgumentException("Failed to create notification for farmer ID: " + farmerId);
        }

        return  notificationResult;
    }

    @Override
    public List<Notification> fetchAllNotificationsByFarmerId(Long farmerId) {
        return queryService.handle(new GetAllNotificationsByFarmerIdQuery(farmerId));
    }

    @Override
    public void deleteNotification(Long notificationId) {
        commandService.handle(new DeleteNotificationCommand(notificationId));
    }
}
