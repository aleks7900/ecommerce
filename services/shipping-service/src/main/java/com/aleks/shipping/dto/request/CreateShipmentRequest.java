package com.aleks.shipping.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateShipmentRequest(

    @NotNull
    UUID orderId,

    @NotBlank
    String address
) {
}