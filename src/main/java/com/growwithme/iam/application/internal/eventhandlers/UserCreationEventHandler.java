package com.growwithme.iam.application.internal.eventhandlers;

import com.growwithme.iam.application.internal.outboundservices.acl.ExternalNotificationService;
import com.growwithme.iam.domain.model.events.UserCreationEvent;
import com.growwithme.iam.domain.model.queries.GetUserByIdQuery;
import com.growwithme.iam.domain.model.valueobjects.Roles;
import com.growwithme.iam.domain.services.UserQueryService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCreationEventHandler {
    private final UserQueryService userQueryService;
    private final ExternalNotificationService externalNotificationService;

    @EventListener(UserCreationEvent.class)
    public void on(UserCreationEvent event) {
        var user = userQueryService.handle(new GetUserByIdQuery(event.getUserId()));

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        if (user.get().getRoles().stream().anyMatch(role -> role.getName() == Roles.FARMER_ROLE)) {
            externalNotificationService.createNotification(user.get().getId(), "Welcome to GrowWithMe!", "Thank you for joining our platform as a farmer. We are excited to have you on board!");
        }
    }

}
