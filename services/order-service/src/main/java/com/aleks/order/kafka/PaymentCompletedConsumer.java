package com.aleks.order.kafka;

import com.aleks.avro.OrderConfirmedEvent;
import com.aleks.avro.PaymentCompletedEvent;
import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.repository.OrderRepository;
import com.aleks.outbox.service.OutboxPublisherService;
import java.util.UUID;
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
        event.getOrderId()
    );

    Order order =
        orderRepository
            .findById(
                UUID.fromString(event.getOrderId())
            )
            .orElse(null);

    if (order == null) {

      log.error(
          "Order {} not found",
          event.getOrderId()
      );

      return;
    }

    order.setStatus(
        OrderStatus.CONFIRMED
    );

    orderRepository.save(order);

    OrderConfirmedEvent confirmedEvent =
        OrderConfirmedEvent.newBuilder()
            .setOrderId(
                order.getId().toString()
            )
            .setConfirmedAt(
                Instant.now().toString()
            )
            .build();

    outboxPublisherService.publish(
        "ORDER",
        order.getId().toString(),
        "order-confirmed",
        confirmedEvent
    );

    log.info(
        "Order {} confirmed",
        order.getId()
    );
  }
}