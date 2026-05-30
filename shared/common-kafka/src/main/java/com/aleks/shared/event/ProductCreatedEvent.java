package com.aleks.shared.event;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ProductCreatedEvent(

    UUID productId,

    String name,

    BigDecimal price,

    UUID sellerId,

    Instant createdAt
) {
}