package com.aleks.inventory.mapper;

import com.aleks.inventory.dto.response.InventoryResponse;
import com.aleks.inventory.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

  public InventoryResponse toResponse(
      Inventory inventory
  ) {

    return new InventoryResponse(
        inventory.getId(),
        inventory.getProductId(),
        inventory.getQuantity(),
        inventory.getReservedQuantity()
    );
  }
}
