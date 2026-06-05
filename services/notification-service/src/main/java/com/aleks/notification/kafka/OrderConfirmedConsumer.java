package com.aleks.notification.kafka;

import com.aleks.avro.NotificationSentEvent;
import com.aleks.avro.OrderConfirmedEvent;
import com.aleks.notification.entity.Notification;
import com.aleks.notification.service.EmailNotificationService;
import com.aleks.notification.service.NotificationService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConfirmedConsumer {

  private final NotificationService notificationService;

  private final EmailNotificationService emailService;

  private final NotificationEventPublisher
      notificationEventPublisher;

  @KafkaListener(
      topics = "order-confirmed",
      groupId = "notification-group"
  )
  public void consume(
      OrderConfirmedEvent event
  ) {

    log.info(
        "Received OrderConfirmedEvent {}",
        event.getOrderId()
    );

    Notification notification =
        Notification.builder()
            .orderId(UUID.fromString(event.getOrderId()))
            .recipient("customer@email.com")
            .subject("Order confirmed")
            .message(
                "Your order "
                    + event.getOrderId()
                    + " has been confirmed."
            )
            .build();

    notificationService.save(
        notification
    );

    Notification savedNotification =
        notificationService.save(
            notification
        );

    NotificationSentEvent sentEvent =
        NotificationSentEvent.newBuilder()
            .setNotificationId(
                savedNotification.getId().toString()
            )
            .setOrderId(
                savedNotification.getOrderId().toString()
            )
            .setRecipient(
                savedNotification.getRecipient()
            )
            .setSentAt(
                savedNotification.getCreatedAt().toString()
            )
            .build();

    notificationEventPublisher
        .publishNotificationSentEvent(
            sentEvent
        );

    emailService.sendEmail(
        notification.getRecipient(),
        notification.getSubject(),
        notification.getMessage()
    );
  }
}