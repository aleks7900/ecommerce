package com.aleks.order.kafka;

import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.aleks.shared.event.OrderConfirmedEvent;
import com.aleks.shared.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

  private final OutboxEventRepository outboxEventRepository;

  private final ObjectMapper objectMapper;

  public void publishOrderCreatedEvent(
      OrderCreatedEvent event
  ) {

    save(
        "ORDER",
        "order-created",
        event.orderId().toString(),
        event
    );
  }

  public void publishOrderConfirmedEvent(
      OrderConfirmedEvent event
  ) {

    save(
        "ORDER",
        "order-confirmed",
        event.orderId().toString(),
        event
    );
  }

  private void save(
      String aggregateType,
      String topic,
      String aggregateId,
      Object event
  ) {

    try {

      outboxEventRepository.save(

          OutboxEvent.builder()
              .aggregateType(
                  aggregateType
              )
              .aggregateId(
                  aggregateId
              )
              .topic(
                  topic
              )
              .payload(
                  objectMapper.writeValueAsString(
                      event
                  )
              )
              .status(
                  OutboxStatus.NEW
              )
              .build()
      );

      log.info(
          "Event saved to outbox. topic={}, aggregateId={}",
          topic,
          aggregateId
      );

    } catch (Exception ex) {

      throw new RuntimeException(
          "Failed to save outbox event",
          ex
      );
    }
  }
}