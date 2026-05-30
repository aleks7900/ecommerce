package com.aleks.shared.event;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record OrderCreatedEvent(

    UUID orderId,

    UUID productId,

    UUID buyerId,

    Integer quantity,

    BigDecimal totalPrice,

    Instant createdAt
) {
}