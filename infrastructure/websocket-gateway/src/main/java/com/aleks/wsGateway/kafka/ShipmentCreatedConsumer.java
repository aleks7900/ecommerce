package com.aleks.wsGateway.kafka;

import com.aleks.shared.event.ShipmentCreatedEvent;
import com.aleks.wsGateway.webSocket.NotificationWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentCreatedConsumer {

  private final NotificationWebSocketHandler handler;

  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = "shipment-created",
      groupId = "ws-gateway"
  )
  public void consume(
      ShipmentCreatedEvent event
  ) throws Exception {

    handler.broadcast(
        objectMapper.writeValueAsString(
            event
        )
    );
  }
}
