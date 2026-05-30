package com.aleks.notification.kafka;

import com.aleks.shared.event.NotificationSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

  private static final String NOTIFICATION_SENT_TOPIC =
      "notification-sent";

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendNotificationSentEvent(
      NotificationSentEvent event
  ) {

    kafkaTemplate.send(
        NOTIFICATION_SENT_TOPIC,
        event.notificationId().toString(),
        event
    );

    log.info(
        "NotificationSentEvent published. notificationId={}",
        event.notificationId()
    );
  }
}