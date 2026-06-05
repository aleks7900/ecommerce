package com.aleks.notification.kafka;

import com.aleks.avro.NotificationSentEvent;
import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {

  private static final String NOTIFICATION_SENT_TOPIC =
      "notification-sent";

  private final OutboxEventRepository outboxEventRepository;

  private final ObjectMapper objectMapper;

  public void publishNotificationSentEvent(
      NotificationSentEvent event
  ) {

    saveOutboxEvent(
        NOTIFICATION_SENT_TOPIC,
        event.getNotificationId(),
        event
    );

    log.info(
        "NotificationSentEvent saved to outbox. notificationId={}",
        event.getNotificationId()
    );
  }

  private void saveOutboxEvent(
      String topic,
      String aggregateId,
      Object payload
  ) {

    try {

      outboxEventRepository.save(

          OutboxEvent.builder()
              .aggregateType("NOTIFICATION")
              .aggregateId(aggregateId)
              .topic(topic)
              .payload(
                  objectMapper.writeValueAsString(
                      payload
                  )
              )
              .status(
                  OutboxStatus.NEW
              )
              .build()
      );

    } catch (Exception ex) {

      throw new RuntimeException(
          "Failed to save outbox event",
          ex
      );
    }
  }
}