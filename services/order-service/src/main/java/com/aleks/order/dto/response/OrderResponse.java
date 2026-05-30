package com.aleks.order.dto.response;

import com.aleks.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(

    UUID id,

    UUID productId,

    UUID buyerId,

    Integer quantity,

    BigDecimal totalPrice,

    OrderStatus status,

    Instant createdAt
) {
}
