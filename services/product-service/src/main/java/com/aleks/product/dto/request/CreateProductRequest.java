package com.aleks.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductRequest(

    @NotBlank
    String name,

    String description,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal price,

    @NotNull
    UUID sellerId
) {
}