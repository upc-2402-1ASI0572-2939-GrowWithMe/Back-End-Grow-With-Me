package com.growwithme.notifications.domain.services;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.domain.model.queries.GetAllNotificationsByFarmerIdQuery;
import java.util.List;

public interface NotificationQueryService {
    List<Notification> handle(GetAllNotificationsByFarmerIdQuery query);
}
