package com.aleks.payment.kafka;

import com.aleks.outbox.service.OutboxPublisherService;
import com.aleks.shared.event.PaymentCompletedEvent;
import com.aleks.shared.event.PaymentFailedEvent;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

  private final OutboxPublisherService outboxPublisherService;

  public void sendPaymentCompleted(
      PaymentCompletedEvent event
  ) {

    outboxPublisherService.publish(
        "PAYMENT",
        event.paymentId().toString(),
        "payment-completed",
        event
    );

    log.info(
        "PaymentCompletedEvent sent for order {}",
        event.orderId()
    );
  }

  public void publishPaymentFailed(
      UUID orderId,
      BigDecimal amount,
      String reason
  ) {

    PaymentFailedEvent event =
        PaymentFailedEvent.builder()
            .orderId(orderId)
            .amount(amount)
            .reason(reason)
            .build();

    outboxPublisherService.publish(
        "PAYMENT",
        orderId.toString(),
        "payment-failed",
        event
    );
  }
}