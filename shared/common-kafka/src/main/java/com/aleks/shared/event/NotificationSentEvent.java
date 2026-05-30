package com.aleks.shared.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record NotificationSentEvent(

    UUID notificationId,

    UUID orderId,

    String recipient,

    Instant sentAt
) {
}