package com.aleks.inventory.kafka;

import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.repository.InventoryRepository;
import com.aleks.outbox.service.OutboxPublisherService;
import com.aleks.shared.event.InventoryReservationFailedEvent;
import com.aleks.shared.event.InventoryReservedEvent;
import com.aleks.shared.event.OrderCreatedEvent;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

  private static final String INVENTORY_RESERVED_TOPIC =
      "inventory-reserved";

  private static final String INVENTORY_RESERVATION_FAILED_TOPIC =
      "inventory-reservation-failed";

  private final InventoryRepository inventoryRepository;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final OutboxPublisherService outboxPublisherService;

  @KafkaListener(
      topics = "order-created",
      groupId = "inventory-group"
  )
  public void consume(
      OrderCreatedEvent event
  ) {

    log.info(
        "Received OrderCreatedEvent {}",
        event.orderId()
    );

    Inventory inventory =
        inventoryRepository
            .findByProductId(
                event.productId()
            )
            .orElse(null);

    if (inventory == null) {

      publishFailed(
          event,
          "Inventory not found"
      );

      return;
    }

    if (inventory.getQuantity()
        < event.quantity()) {

      publishFailed(
          event,
          "Not enough stock"
      );

      return;
    }

    inventory.setQuantity(
        inventory.getQuantity()
            - event.quantity()
    );

    inventory.setReservedQuantity(
        inventory.getReservedQuantity()
            + event.quantity()
    );

    inventoryRepository.save(
        inventory
    );

    InventoryReservedEvent reservedEvent =
        InventoryReservedEvent
            .builder()
            .orderId(
                event.orderId()
            )
            .productId(
                event.productId()
            )
            .quantity(
                event.quantity()
            )
            .reservedAt(
                Instant.now()
            )
            .build();

    outboxPublisherService.publish(
        "INVENTORY",
        event.orderId().toString(),
        INVENTORY_RESERVED_TOPIC,
        reservedEvent
    );

    log.info(
        "Inventory reserved for order {}",
        event.orderId()
    );
  }

  private void publishFailed(
      OrderCreatedEvent event,
      String reason
  ) {

    InventoryReservationFailedEvent failedEvent =
        InventoryReservationFailedEvent
            .builder()
            .orderId(
                event.orderId()
            )
            .productId(
                event.productId()
            )
            .requestedQuantity(
                event.quantity()
            )
            .reason(reason)
            .failedAt(
                Instant.now()
            )
            .build();

    outboxPublisherService.publish(
        "INVENTORY",
        event.orderId().toString(),
        INVENTORY_RESERVATION_FAILED_TOPIC,
        failedEvent
    );

    log.warn(
        "Inventory reservation failed for order {}. Reason: {}",
        event.orderId(),
        reason
    );
  }
}
