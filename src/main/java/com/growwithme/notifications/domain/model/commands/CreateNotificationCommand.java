package com.growwithme.notifications.domain.model.commands;

public record CreateNotificationCommand(Long farmerId, String title, String message) {
}
