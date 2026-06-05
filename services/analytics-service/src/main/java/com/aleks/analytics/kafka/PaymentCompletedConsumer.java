package com.aleks.analytics.kafka;

import com.aleks.analytics.service.AnalyticsService;
import com.aleks.avro.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PaymentCompletedConsumer {

  private final AnalyticsService analyticsService;

  @KafkaListener(
      topics = "payment-completed",
      groupId = "analytics-group"
  )
  public void consume(
      PaymentCompletedEvent event
  ) {

    analyticsService.paymentCompleted(
        BigDecimal.ZERO
    );
  }
}