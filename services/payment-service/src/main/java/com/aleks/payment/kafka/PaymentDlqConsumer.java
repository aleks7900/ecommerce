package com.aleks.payment.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentDlqConsumer {

  @KafkaListener(
      topics = "inventory-reserved-dlt",
      groupId = "payment-dlt-group"
  )
  public void consume(
      String payload
  ) {

    log.error(
        "DLQ MESSAGE RECEIVED: {}",
        payload
    );
  }
}