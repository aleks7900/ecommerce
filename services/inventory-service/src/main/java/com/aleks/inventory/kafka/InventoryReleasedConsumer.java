package com.aleks.inventory.kafka;

import com.aleks.avro.InventoryReleasedEvent;
import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.repository.InventoryRepository;
import java.util.UUID;
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
        event.getOrderId()
    );

    Inventory inventory =
        inventoryRepository
            .findByProductId(
                UUID.fromString(event.getProductId())
            )
            .orElse(null);

    if (inventory == null) {

      log.error(
          "Inventory not found for product {}",
          event.getProductId()
      );

      return;
    }

    inventory.setQuantity(
        inventory.getQuantity()
            + event.getQuantity()
    );

    inventory.setReservedQuantity(
        inventory.getReservedQuantity()
            - event.getQuantity()
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