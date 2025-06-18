package com.growwithme.notifications.application.internal.queryservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.domain.model.queries.GetAllNotificationsByFarmerIdQuery;
import com.growwithme.notifications.domain.services.NotificationQueryService;
import com.growwithme.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository repository;

    @Override
    public List<Notification> handle(GetAllNotificationsByFarmerIdQuery query) {
        return repository.findAllByFarmerUser_Id(query.farmerId());
    }
}
