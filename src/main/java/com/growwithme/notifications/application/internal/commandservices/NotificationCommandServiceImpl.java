package com.growwithme.notifications.application.internal.commandservices;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.crops.application.internal.outboundservices.acl.ExternalFarmerUserService;
import com.growwithme.notifications.domain.model.aggregates.Notification;
import com.growwithme.notifications.domain.model.commands.CreateNotificationCommand;
import com.growwithme.notifications.domain.model.commands.DeleteNotificationCommand;
import com.growwithme.notifications.domain.services.NotificationCommandService;
import com.growwithme.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository repository;
    private final ExternalFarmerUserService externalFarmerUserService;

    @Override
    public Optional<Notification> handle(CreateNotificationCommand command) {
        var farmerUserResult = externalFarmerUserService.fetchFarmerUserById(command.farmerId());

        var newNotification = new Notification(
                farmerUserResult,
                command.title(),
                command.message()
        );

        try {
            var savedNotification = repository.save(newNotification);
            return Optional.of(savedNotification);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error creating notification: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(DeleteNotificationCommand command) {
        try {
            var notificationResult = repository.findById(command.id());

            if (notificationResult.isEmpty()) {
                throw new IllegalArgumentException("Notification not found with ID: " + command.id());
            }

            repository.deleteById(command.id());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting notification: " + e.getMessage(), e);
        }
    }
}
