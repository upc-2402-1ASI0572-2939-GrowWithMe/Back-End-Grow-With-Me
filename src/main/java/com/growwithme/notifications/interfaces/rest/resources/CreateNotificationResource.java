package com.growwithme.notifications.interfaces.rest.resources;

public record CreateNotificationResource(
        Long farmerId,
        String title,
        String message
) {
}
