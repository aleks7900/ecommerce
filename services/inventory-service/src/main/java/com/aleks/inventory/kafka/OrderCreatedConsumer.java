package com.aleks.inventory.kafka;

import static com.aleks.avro.util.AvroJsonUtils.toJson;
import com.aleks.avro.InventoryReservationFailedEvent;
import com.aleks.avro.InventoryReservedEvent;
import com.aleks.avro.OrderCreatedEvent;
import com.aleks.inventory.entity.Inventory;
import com.aleks.inventory.repository.InventoryRepository;
import com.aleks.inventory.service.InventoryService;
import com.aleks.outbox.service.OutboxPublisherService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
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

  private final InventoryService inventoryService;

  private final OutboxPublisherService outboxPublisherService;

  @KafkaListener(
      topics = "order-created",
      groupId = "inventory-group",
  containerFactory = "orderKafkaListenerContainerFactory"
  )
  public void consume(
      OrderCreatedEvent event
  ) {

    log.info(
        "Received OrderCreatedEvent {}",
        event.getOrderId()
    );

    Inventory inventory =
        inventoryRepository
            .findByProductId(
                UUID.fromString(event.getProductId())
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
        < event.getQuantity()) {

      publishFailed(
          event,
          "Not enough stock"
      );

      return;
    }

    inventory.setQuantity(
        inventory.getQuantity()
            - event.getQuantity()
    );

    inventory.setReservedQuantity(
        inventory.getReservedQuantity()
            + event.getQuantity()
    );

    inventoryService.reserveStock(

        UUID.fromString(event.getProductId()),

        event.getQuantity()
    );

    InventoryReservedEvent reservedEvent =
        InventoryReservedEvent.newBuilder()
            .setOrderId(
                event.getOrderId()
            )
            .setProductId(
                event.getProductId()
            )
            .setQuantity(
                event.getQuantity()
            )
            .setReservedAt(
                Instant.now().toString()
            )
            .build();

    outboxPublisherService.publish(
        "INVENTORY",
        event.getOrderId(),
        INVENTORY_RESERVED_TOPIC,
        reservedEvent
    );

    log.info(
        "Inventory reserved for order {}",
        event.getOrderId()
    );
  }

  private void publishFailed(
      OrderCreatedEvent event,
      String reason
  ) {

    InventoryReservationFailedEvent failedEvent =
        InventoryReservationFailedEvent.newBuilder()
            .setOrderId(
                event.getOrderId().toString()
            )
            .setProductId(
                event.getProductId().toString()
            )
            .setReason(
                reason
            )
            .setFailedAt(
                Instant.now().toString()
            )
            .build();

    outboxPublisherService.publish(
        "INVENTORY",
        event.getOrderId().toString(),
        INVENTORY_RESERVATION_FAILED_TOPIC,
        failedEvent
    );

    log.warn(
        "Inventory reservation failed for order {}. Reason: {}",
        event.getOrderId(),
        reason
    );
  }
}
