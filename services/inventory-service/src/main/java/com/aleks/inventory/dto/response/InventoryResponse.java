package com.aleks.inventory.dto.response;

import java.util.UUID;

public record InventoryResponse(

    UUID id,

    UUID productId,

    Integer quantity,

    Integer reservedQuantity
) {
}