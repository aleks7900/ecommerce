package com.aleks.shipping.event;

import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.aleks.shared.event.ShipmentCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingEventPublisher {

  private final OutboxEventRepository repository;

  private final ObjectMapper objectMapper;

  public void publishShipmentCreated(
      ShipmentCreatedEvent event
  ) {

    try {

      repository.save(

          OutboxEvent.builder()
              .aggregateType("SHIPMENT")
              .aggregateId(
                  event.shipmentId().toString()
              )
              .topic(
                  "shipment-created"
              )
              .payload(
                  objectMapper.writeValueAsString(
                      event
                  )
              )
              .status(
                  OutboxStatus.NEW
              )
              .build()
      );

      log.info(
          "ShipmentCreatedEvent saved to outbox. shipmentId={}",
          event.shipmentId()
      );

    } catch (Exception ex) {

      throw new RuntimeException(
          "Failed to save outbox event",
          ex
      );
    }
  }
}