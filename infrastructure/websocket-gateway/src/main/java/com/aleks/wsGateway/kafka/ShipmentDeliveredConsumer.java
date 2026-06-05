package com.aleks.wsGateway.kafka;

import com.aleks.shared.event.ShipmentDeliveredEvent;
import com.aleks.wsGateway.webSocket.NotificationWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentDeliveredConsumer {

  private final NotificationWebSocketHandler handler;

  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = "shipment-delivered",
      groupId = "ws-gateway"
  )
  public void consume(
      ShipmentDeliveredEvent event
  ) throws Exception {

    handler.broadcast(
        objectMapper.writeValueAsString(
            event
        )
    );
  }
}