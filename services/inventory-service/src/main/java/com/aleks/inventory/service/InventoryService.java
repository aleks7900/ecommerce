package com.aleks.inventory.service;

import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.exception.InventoryNotFoundException;
import com.aleks.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  public Inventory create(
      Inventory inventory
  ) {

    return inventoryRepository.save(inventory);
  }

  public Inventory getById(
      UUID id
  ) {

    return inventoryRepository.findById(id)
        .orElseThrow(() ->
            new InventoryNotFoundException(
                "Inventory not found: " + id
            )
        );
  }

  public Inventory getByProductId(
      UUID productId
  ) {

    return inventoryRepository.findByProductId(productId)
        .orElseThrow(() ->
            new InventoryNotFoundException(
                "Inventory not found for product: "
                    + productId
            )
        );
  }

  public List<Inventory> getAll() {

    return inventoryRepository.findAll();
  }

  public Inventory updateQuantity(
      UUID id,
      Integer quantity
  ) {

    Inventory inventory = getById(id);

    inventory.setQuantity(quantity);

    return inventoryRepository.save(inventory);
  }

  public void delete(
      UUID id
  ) {

    inventoryRepository.delete(
        getById(id)
    );
  }
}
