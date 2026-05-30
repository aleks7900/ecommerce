package com.aleks.order.kafka;

import com.aleks.shared.event.OrderConfirmedEvent;
import com.aleks.shared.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

  private static final String ORDER_CREATED_TOPIC =
      "order-created";

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendOrderCreatedEvent(
      OrderCreatedEvent event
  ) {

    log.info(
        "Publishing OrderCreatedEvent {}",
        event.orderId()
    );

    kafkaTemplate.send(
        ORDER_CREATED_TOPIC,
        event.orderId().toString(),
        event
    );
  }

  public void sendOrderConfirmedEvent(
      OrderConfirmedEvent event
  ) {

    kafkaTemplate.send(
        "order-confirmed",
        event.orderId().toString(),
        event
    );

    log.info(
        "OrderConfirmedEvent sent for order {}",
        event.orderId()
    );
  }
}