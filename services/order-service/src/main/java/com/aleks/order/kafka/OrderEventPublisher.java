package com.aleks.order.kafka;

import static com.aleks.avro.util.AvroJsonUtils.toJson;
import com.aleks.avro.OrderConfirmedEvent;
import com.aleks.avro.OrderCreatedEvent;
import com.aleks.avro.util.AvroJsonUtils;
import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.aleks.outbox.service.OutboxPublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {


  private static final String ORDER_CREATED_TOPIC =
      "order-created";

  private static final String ORDER_UPDATED_TOPIC =
      "order-updated";

  private final OutboxPublisherService outboxPublisherService;

  public void publishOrderCreatedEvent(
      OrderCreatedEvent event
  ) {

    log.info(
        "Publishing OrderCreatedEvent for order {}",
        event.getOrderId()
    );

    outboxPublisherService.publish(
        "ORDER",
        String.valueOf(event.getOrderId()),
        ORDER_CREATED_TOPIC,
        event
    );
  }

  public void publishOrderConfirmedEvent(
      OrderConfirmedEvent event
  ) {

    log.info(
        "Publishing OrderConfirmedEvent for order {}",
        event.getOrderId()
    );

    outboxPublisherService.publish(
        "ORDER",
        String.valueOf(event.getOrderId()),
        ORDER_UPDATED_TOPIC,
        event
    );
  }
}