package com.aleks.inventory.controller;

import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Inventory create(
      @RequestBody Inventory inventory
  ) {

    return inventoryService.create(
        inventory
    );
  }

  @GetMapping("/{id}")
  public Inventory getById(
      @PathVariable UUID id
  ) {

    return inventoryService.getById(id);
  }

  @GetMapping("/product/{productId}")
  public Inventory getByProductId(
      @PathVariable UUID productId
  ) {

    return inventoryService.getByProductId(productId);
  }

  @GetMapping
  public List<Inventory> getAll() {

    return inventoryService.getAll();
  }

  @PatchMapping("/{id}/quantity")
  public Inventory updateQuantity(
      @PathVariable UUID id,
      @RequestParam Integer quantity
  ) {

    return inventoryService.updateQuantity(
        id,
        quantity
    );
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable UUID id
  ) {

    inventoryService.delete(id);
  }
}
