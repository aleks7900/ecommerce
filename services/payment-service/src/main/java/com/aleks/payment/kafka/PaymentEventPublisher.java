package com.aleks.payment.kafka;

import com.aleks.avro.PaymentCompletedEvent;
import com.aleks.avro.PaymentFailedEvent;
import com.aleks.outbox.service.OutboxPublisherService;
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
        event.getPaymentId().toString(),
        "payment-completed",
        event
    );

    log.info(
        "PaymentCompletedEvent sent for order {}",
        event.getOrderId()
    );
  }

  public void publishPaymentFailed(
      UUID orderId,
      BigDecimal amount,
      String reason
  ) {

    PaymentFailedEvent event =
        PaymentFailedEvent.newBuilder()
            .setOrderId(
                orderId.toString()
            )
            .setAmount(
                amount.doubleValue()
            )
            .setReason(
                reason
            )
            .build();

    outboxPublisherService.publish(
        "PAYMENT",
        orderId.toString(),
        "payment-failed",
        event
    );
  }
}