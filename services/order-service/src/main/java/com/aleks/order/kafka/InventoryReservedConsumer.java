package com.aleks.order.kafka;

import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.repository.OrderRepository;
import com.aleks.shared.event.InventoryReservedEvent;
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
        event.orderId()
    );

    Order order =
        orderRepository
            .findById(
                event.orderId()
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