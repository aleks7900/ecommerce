package com.aleks.shared.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record InventoryReservedEvent(

    UUID orderId,

    UUID productId,

    Integer quantity,

    Instant reservedAt
) {
}