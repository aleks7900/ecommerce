package com.aleks.analytics.kafka;

import com.aleks.analytics.entity.AnalyticsOrder;
import com.aleks.analytics.repository.AnalyticsRepository;
import com.aleks.analytics.service.AnalyticsService;
import com.aleks.avro.OrderCreatedEvent;
import com.aleks.outbox.dto.DebeziumMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

  private final AnalyticsService analyticsService;

  private final AnalyticsRepository repository;

  @KafkaListener(
      topics = "order-created",
      groupId = "analytics-group",
      containerFactory = "orderKafkaListenerContainerFactory"
  )
  public void consume(OrderCreatedEvent event) {

    AnalyticsOrder analyticsOrder =
        AnalyticsOrder.builder()
            .orderId(UUID.fromString(event.getOrderId()))
            .customerId(UUID.fromString(event.getBuyerId()))
            .totalPrice(BigDecimal.valueOf(event.getTotalPrice()))
            .createdAt(Instant.now())
            .build();

    repository.save(analyticsOrder);

    analyticsService.orderCreated(event);
  }
}