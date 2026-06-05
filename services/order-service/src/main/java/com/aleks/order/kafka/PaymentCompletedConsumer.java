package com.aleks.order.kafka;

import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.repository.OrderRepository;
import com.aleks.outbox.service.OutboxPublisherService;
import com.aleks.shared.event.OrderConfirmedEvent;
import com.aleks.shared.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedConsumer {

  private final OrderRepository orderRepository;

  private final OutboxPublisherService outboxPublisherService;

  @KafkaListener(
      topics = "payment-completed",
      groupId = "order-group"
  )
  public void consume(
      PaymentCompletedEvent event
  ) {

    log.info(
        "Received PaymentCompletedEvent {}",
        event.orderId()
    );

    Order order =
        orderRepository
            .findById(
                event.orderId()
            )
            .orElse(null);

    if (order == null) {

      log.error(
          "Order {} not found",
          event.orderId()
      );

      return;
    }

    order.setStatus(
        OrderStatus.CONFIRMED
    );

    orderRepository.save(order);

    OrderConfirmedEvent confirmedEvent =
        OrderConfirmedEvent
            .builder()
            .orderId(order.getId())
            .confirmedAt(Instant.now())
            .build();

    outboxPublisherService.publish(
        "ORDER",
        order.getId().toString(),
        "order-confirmed",
        event
    );

    log.info(
        "Order {} confirmed",
        order.getId()
    );
  }
}