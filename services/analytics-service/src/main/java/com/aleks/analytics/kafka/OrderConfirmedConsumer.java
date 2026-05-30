package com.aleks.analytics.kafka;

import com.aleks.analytics.service.AnalyticsService;
import com.aleks.shared.event.OrderConfirmedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConfirmedConsumer {

  private final AnalyticsService analyticsService;

  @KafkaListener(
      topics = "order-confirmed",
      groupId = "analytics-group"
  )
  public void consume(
      OrderConfirmedEvent event
  ) {

    analyticsService.orderConfirmed();
  }
}