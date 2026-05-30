package com.aleks.shared.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record InventoryReleasedEvent(

    UUID orderId,

    UUID productId,

    Integer quantity,

    Instant releasedAt
) {
}