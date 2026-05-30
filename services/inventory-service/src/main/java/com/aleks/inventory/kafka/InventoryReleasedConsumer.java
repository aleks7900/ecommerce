package com.aleks.inventory.kafka;

import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.repository.InventoryRepository;
import com.aleks.shared.event.InventoryReleasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryReleasedConsumer {

  private final InventoryRepository inventoryRepository;

  @KafkaListener(
      topics = "inventory-released",
      groupId = "inventory-group"
  )
  public void consume(
      InventoryReleasedEvent event
  ) {

    log.info(
        "Received InventoryReleasedEvent for order {}",
        event.orderId()
    );

    Inventory inventory =
        inventoryRepository
            .findByProductId(
                event.productId()
            )
            .orElse(null);

    if (inventory == null) {

      log.error(
          "Inventory not found for product {}",
          event.productId()
      );

      return;
    }

    inventory.setQuantity(
        inventory.getQuantity()
            + event.quantity()
    );

    inventory.setReservedQuantity(
        inventory.getReservedQuantity()
            - event.quantity()
    );

    inventoryRepository.save(
        inventory
    );

    log.info(
        "Inventory released for product {}. quantity={}, reserved={}",
        inventory.getProductId(),
        inventory.getQuantity(),
        inventory.getReservedQuantity()
    );
  }
}