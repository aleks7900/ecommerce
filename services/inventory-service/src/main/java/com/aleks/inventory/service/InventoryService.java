package com.aleks.inventory.service;

import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.exception.InventoryNotFoundException;
import com.aleks.inventory.repository.InventoryRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  private final RedissonClient redissonClient;

  @Transactional
  public void reserveStock(
      UUID productId,
      Integer quantity
  ) {

    String lockKey =
        "inventory:" + productId;

    RLock lock =
        redissonClient.getLock(lockKey);

    boolean acquired = false;

    try {

      acquired = lock.tryLock(
          5,
          30,
          TimeUnit.SECONDS
      );

      if (!acquired) {

        throw new RuntimeException(
            "Could not acquire lock"
        );
      }

      Inventory inventory =
          inventoryRepository.findByProductId(
                  productId
              )
              .orElseThrow(
                  () -> new InventoryNotFoundException("Product not found")
              );

      if (
          inventory.getAvailableQuantity()
              < quantity
      ) {
        throw new IllegalStateException(
            "Not enough stock"
        );
      }

      inventory.setAvailableQuantity(

          inventory.getAvailableQuantity()
              - quantity
      );

      inventoryRepository.save(
          inventory
      );

      log.info(
          "Stock reserved. productId={} qty={}",
          productId,
          quantity
      );

    } catch (InterruptedException e) {

      Thread.currentThread()
          .interrupt();

      throw new RuntimeException(e);

    } finally {

      if (
          acquired &&
              lock.isHeldByCurrentThread()
      ) {

        lock.unlock();
      }
    }
  }

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
