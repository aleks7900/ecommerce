package com.aleks.order.kafka;

import com.aleks.avro.InventoryReleasedEvent;
import com.aleks.avro.PaymentFailedEvent;
import com.aleks.order.entity.Order;
import com.aleks.order.entity.OrderStatus;
import com.aleks.order.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailedConsumer {

  private static final String INVENTORY_RELEASED_TOPIC =
      "inventory-released";

  private final OrderRepository orderRepository;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(
      topics = "payment-failed",
      groupId = "order-group"
  )
  public void consume(
      PaymentFailedEvent event
  ) {

    log.warn(
        "Received PaymentFailedEvent for order {}",
        event.getOrderId()
    );

    Order order =
        orderRepository.findById(
            UUID.fromString(event.getOrderId())
        ).orElse(null);

    if (order == null) {

      log.error(
          "Order {} not found",
          event.getOrderId()
      );

      return;
    }

    order.setStatus(
        OrderStatus.CANCELLED
    );

    orderRepository.save(order);

    InventoryReleasedEvent releasedEvent =
        InventoryReleasedEvent.newBuilder()
            .setOrderId(
                order.getId().toString()
            )
            .setProductId(
                order.getProductId().toString()
            )
            .setQuantity(
                order.getQuantity()
            )
            .setReleasedAt(
                Instant.now().toString()
            )
            .build();

    kafkaTemplate.send(
        INVENTORY_RELEASED_TOPIC,
        order.getId().toString(),
        releasedEvent
    );

    log.info(
        "Order {} cancelled and inventory release requested",
        order.getId()
    );
  }
}