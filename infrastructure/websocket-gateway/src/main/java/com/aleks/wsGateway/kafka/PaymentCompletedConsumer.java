package com.aleks.wsGateway.kafka;

import com.aleks.avro.PaymentCompletedEvent;
import com.aleks.wsGateway.webSocket.NotificationWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCompletedConsumer {

  private final NotificationWebSocketHandler handler;

  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = "payment-completed",
      groupId = "ws-gateway"
  )
  public void consume(
      PaymentCompletedEvent event
  ) throws Exception {

    handler.broadcast(
        objectMapper.writeValueAsString(
            event
        )
    );
  }
}