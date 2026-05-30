package com.aleks.product.dto.request;
import com.aleks.shared.event.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductRequest(

    @NotBlank
    String name,

    String description,

    @NotNull
    @DecimalMin("0.01")
    BigDecimal price,

    @NotNull
    ProductStatus status
) {
}