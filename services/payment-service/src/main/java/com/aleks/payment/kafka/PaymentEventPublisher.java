package com.aleks.payment.kafka;

import com.aleks.shared.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendPaymentCompleted(
      PaymentCompletedEvent event
  ) {

    kafkaTemplate.send(
        "payment-completed",
        event.orderId().toString(),
        event
    );

    log.info(
        "PaymentCompletedEvent sent for order {}",
        event.orderId()
    );
  }
}