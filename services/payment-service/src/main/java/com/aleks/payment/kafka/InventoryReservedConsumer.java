package com.aleks.payment.kafka;

import com.aleks.avro.InventoryReservedEvent;
import com.aleks.avro.PaymentCompletedEvent;
import com.aleks.payment.entity.Payment;
import com.aleks.payment.service.PaymentService;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryReservedConsumer {

  private final PaymentService paymentService;

  private final PaymentEventPublisher paymentEventPublisher;

  @RetryableTopic(
      attempts = "3",
      dltTopicSuffix = "-dlt"
  )
  @KafkaListener(
      topics = "inventory-reserved",
      groupId = "payment-group"
  )
  public void consume(
      InventoryReservedEvent event
  ) {

    log.info(
        "Received InventoryReservedEvent {}",
        event.getOrderId()
    );

    Payment payment =
        paymentService.createPayment(
            UUID.fromString(event.getOrderId()),
            null,
            BigDecimal.valueOf(10.0)
        );

    boolean success = new Random().nextBoolean();

    if (success) {
      PaymentCompletedEvent completedEvent =
          PaymentCompletedEvent.newBuilder()
              .setPaymentId(
                  payment.getId().toString()
              )
              .setOrderId(
                  event.getOrderId()
              )
              .setCompletedAt(
                  Instant.now().toString()
              )
              .build();
      paymentEventPublisher.sendPaymentCompleted(
          completedEvent
      );

    } else {
      paymentEventPublisher.publishPaymentFailed(
          payment.getOrderId(), payment.getAmount(), "fail"
      );
    }
  }
}