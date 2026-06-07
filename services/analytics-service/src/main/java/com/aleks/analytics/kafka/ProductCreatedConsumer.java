package com.aleks.analytics.kafka;

import com.aleks.analytics.service.AnalyticsService;
import com.aleks.avro.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreatedConsumer {


  private final AnalyticsService analyticsService;

  @KafkaListener(
      topics = "product-created",
      groupId = "analytics-group",
      containerFactory = "productKafkaListenerContainerFactory"
  )
  public void consume(
      ProductCreatedEvent event
  ) throws Exception {

    analyticsService.productCreated();
  }
}