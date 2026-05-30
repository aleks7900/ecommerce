package com.aleks.notification.dto.response;

import com.aleks.notification.entity.NotificationStatus;

import java.time.Instant;
import java.util.UUID;

public record NotificationResponse(

    UUID id,

    UUID orderId,

    String recipient,

    String subject,

    String message,

    NotificationStatus status,

    Instant createdAt
) {
}