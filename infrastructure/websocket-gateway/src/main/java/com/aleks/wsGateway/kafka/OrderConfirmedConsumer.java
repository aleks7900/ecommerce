package com.aleks.wsGateway.kafka;

import com.aleks.avro.OrderConfirmedEvent;
import com.aleks.wsGateway.webSocket.NotificationWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConfirmedConsumer {

  private final NotificationWebSocketHandler handler;

  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = "order-confirmed",
      groupId = "ws-gateway"
  )
  public void consume(
      OrderConfirmedEvent event
  ) throws Exception {

    handler.broadcast(

        objectMapper.writeValueAsString(
            event
        )
    );
  }
}
