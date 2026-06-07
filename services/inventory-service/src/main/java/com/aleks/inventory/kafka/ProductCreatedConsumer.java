package com.aleks.inventory.kafka;

import com.aleks.avro.ProductCreatedEvent;
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
public class ProductCreatedConsumer {

  private final InventoryRepository inventoryRepository;

  @KafkaListener(
      topics = "product-created",
      groupId = "inventory-group",
      containerFactory = "productKafkaListenerContainerFactory"
  )
  public void consume(
      ProductCreatedEvent event
  ) {

    log.info(
        "Received ProductCreatedEvent: {}",
        event.getProductId()
    );

    Inventory inventory =
        Inventory.builder()
            .productId(
                UUID.fromString(event.getProductId())
            )
            .quantity(0)
            .reservedQuantity(0)
            .build();

    inventoryRepository.save(
        inventory
    );

    log.info(
        "Inventory created for product {}",
        event.getProductId()
    );
  }
}