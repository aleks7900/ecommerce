package com.aleks.inventory.kafka;

import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.repository.InventoryRepository;
import com.aleks.shared.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreatedConsumer {

  private final InventoryRepository inventoryRepository;

  @KafkaListener(
      topics = "product-created",
      groupId = "inventory-group"
  )
  public void consume(
      ProductCreatedEvent event
  ) {

    log.info(
        "Received ProductCreatedEvent: {}",
        event.productId()
    );

    Inventory inventory =
        Inventory.builder()
            .productId(
                event.productId()
            )
            .quantity(0)
            .reservedQuantity(0)
            .build();

    inventoryRepository.save(
        inventory
    );

    log.info(
        "Inventory created for product {}",
        event.productId()
    );
  }
}