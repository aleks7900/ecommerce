package com.aleks.analytics.kafka;

import com.aleks.analytics.service.AnalyticsService;
import com.aleks.shared.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

  private final AnalyticsService analyticsService;

  @KafkaListener(
      topics = "order-created",
      groupId = "analytics-group"
  )
  public void consume(
      OrderCreatedEvent event
  ) {

    analyticsService.orderCreated(event);
  }
}