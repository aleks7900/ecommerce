package com.aleks.shipping.dto.response;

import com.aleks.shipping.entity.ShipmentStatus;

import java.time.Instant;
import java.util.UUID;

public record ShipmentResponse(

    UUID id,

    UUID orderId,

    String address,

    ShipmentStatus status,

    Instant createdAt
) {
}