package com.aleks.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(

    @NotNull
    UUID productId,

    @NotNull
    UUID buyerId,

    @NotNull
    @Min(1)
    Integer quantity,

    @NotNull
    BigDecimal totalPrice
) {
}