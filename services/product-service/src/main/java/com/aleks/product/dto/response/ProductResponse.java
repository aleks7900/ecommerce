package com.aleks.product.dto.response;

import com.aleks.shared.event.ProductStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(

    UUID id,

    String name,

    String description,

    BigDecimal price,

    ProductStatus status,

    UUID sellerId,

    Instant createdAt,

    Instant updatedAt
) {
}