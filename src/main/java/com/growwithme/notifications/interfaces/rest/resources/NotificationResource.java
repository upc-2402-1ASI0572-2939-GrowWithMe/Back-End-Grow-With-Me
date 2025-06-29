package com.growwithme.notifications.interfaces.rest.resources;

public record NotificationResource(
        Long id,
        Long farmerId,
        String title,
        String message
) {
}
