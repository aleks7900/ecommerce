package com.aleks.shared.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record InventoryReservationFailedEvent(

    UUID orderId,

    UUID productId,

    Integer requestedQuantity,

    String reason,

    Instant failedAt
) {
}