package com.aleks.shared.event;

import java.math.BigDecimal;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record PaymentCompletedEvent(

    UUID paymentId,

    UUID orderId,

    BigDecimal amount,

    Instant completedAt
) {
}