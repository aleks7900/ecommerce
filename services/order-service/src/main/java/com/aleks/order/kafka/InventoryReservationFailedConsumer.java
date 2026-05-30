package com.aleks.order.kafka;

import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.repository.OrderRepository;
import com.aleks.shared.event.InventoryReservationFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryReservationFailedConsumer {

  private final OrderRepository orderRepository;

  @KafkaListener(
      topics = "inventory-reservation-failed",
      groupId = "order-group"
  )
  public void consume(
      InventoryReservationFailedEvent event
  ) {

    log.warn(
        "Received InventoryReservationFailedEvent for order {}. Reason: {}",
        event.orderId(),
        event.reason()
    );

    Order order =
        orderRepository
            .findById(event.orderId())
            .orElse(null);

    if (order == null) {

      log.error(
          "Order {} not found",
          event.orderId()
      );

      return;
    }

    order.setStatus(
        OrderStatus.CANCELLED
    );

    orderRepository.save(order);

    log.info(
        "Order {} cancelled",
        order.getId()
    );
  }
}