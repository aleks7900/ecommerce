package com.aleks.payment.kafka;

import com.aleks.payment.entity.Payment;
import com.aleks.payment.service.PaymentService;
import com.aleks.shared.event.InventoryReservedEvent;
import com.aleks.shared.event.PaymentCompletedEvent;
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

  private final PaymentEventProducer paymentEventProducer;

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
        event.orderId()
    );

    Payment payment =
        paymentService.createPayment(
            event.orderId(),
            null,
            null
        );

    PaymentCompletedEvent completedEvent =
        PaymentCompletedEvent.builder()
            .paymentId(payment.getId())
            .orderId(event.orderId())
            .completedAt(Instant.now())
            .build();

    paymentEventProducer.sendPaymentCompleted(
        completedEvent
    );
  }
}