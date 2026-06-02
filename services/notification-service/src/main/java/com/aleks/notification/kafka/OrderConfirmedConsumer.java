package com.aleks.notification.kafka;

import com.aleks.notification.entity.Notification;
import com.aleks.notification.service.EmailNotificationService;
import com.aleks.notification.service.NotificationService;
import com.aleks.shared.event.NotificationSentEvent;
import com.aleks.shared.event.OrderConfirmedEvent;
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
        event.orderId()
    );

    Notification notification =
        Notification.builder()
            .orderId(event.orderId())
            .recipient("customer@email.com")
            .subject("Order confirmed")
            .message(
                "Your order "
                    + event.orderId()
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
        NotificationSentEvent.builder()
            .notificationId(
                savedNotification.getId()
            )
            .orderId(
                savedNotification.getOrderId()
            )
            .recipient(
                savedNotification.getRecipient()
            )
            .sentAt(
                savedNotification.getCreatedAt()
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