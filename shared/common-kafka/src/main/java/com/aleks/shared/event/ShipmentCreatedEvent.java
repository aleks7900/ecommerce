package com.aleks.shared.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ShipmentCreatedEvent(

    UUID shipmentId,

    UUID orderId,

    Instant createdAt
) {
}