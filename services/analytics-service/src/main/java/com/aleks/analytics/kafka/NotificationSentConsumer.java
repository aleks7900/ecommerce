package com.aleks.analytics.kafka;

import com.aleks.analytics.service.AnalyticsService;
import com.aleks.avro.NotificationSentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSentConsumer {

  private final AnalyticsService analyticsService;

  @KafkaListener(
      topics = "notification-sent",
      groupId = "analytics-group"
  )
  public void consume(
      NotificationSentEvent event
  ) {

    analyticsService.notificationSent();
  }
}