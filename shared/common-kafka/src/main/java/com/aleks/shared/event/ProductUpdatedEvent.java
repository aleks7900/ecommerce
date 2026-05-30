package com.aleks.shared.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductUpdatedEvent(

    UUID productId,

    BigDecimal price,

    ProductStatus status,

    Instant updatedAt
) {
}