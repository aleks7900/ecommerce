package com.aleks.order.kafka;

import com.aleks.avro.InventoryReservedEvent;
import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryReservedConsumer {

  private final OrderRepository orderRepository;

  @KafkaListener(
      topics = "inventory-reserved",
      groupId = "order-group"
  )
  public void consume(
      InventoryReservedEvent event
  ) {

    log.info(
        "Received InventoryReservedEvent {}",
        event.getOrderId()
    );

    Order order =
        orderRepository
            .findById(
                UUID.fromString(event.getOrderId())
            )
            .orElse(null);

    if (order == null) {
      return;
    }

    order.setStatus(
        OrderStatus.INVENTORY_RESERVED
    );

    orderRepository.save(order);

    log.info(
        "Order {} status changed to INVENTORY_RESERVED",
        order.getId()
    );
  }
}