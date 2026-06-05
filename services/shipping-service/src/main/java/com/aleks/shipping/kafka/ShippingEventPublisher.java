package com.aleks.shipping.event;

import com.aleks.outbox.service.OutboxPublisherService;
import com.aleks.shared.event.ShipmentCreatedEvent;
import com.aleks.shared.event.ShipmentDeliveredEvent;
import com.aleks.shipping.entity.Shipment;
import com.aleks.shipping.entity.ShipmentStatus;
import com.aleks.shipping.exception.ShipmentNotFoundException;
import com.aleks.shipping.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingEventPublisher {

  private final OutboxPublisherService outboxPublisherService;

  private final ShipmentRepository repository;

  public void publishShipmentCreated(
      ShipmentCreatedEvent event
  ) {

    try {

      outboxPublisherService.publish(
          "SHIPMENT",
          event.shipmentId().toString(),
          "shipment-created",

          event
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

  @Transactional
  public void markAsDelivered(
      UUID shipmentId
  ) {

    Shipment shipment =
        repository.findById(
            shipmentId
        ).orElseThrow(
            ()-> new ShipmentNotFoundException("Shipment with id: " + shipmentId)
        );

    shipment.setStatus(
        ShipmentStatus.DELIVERED
    );

    repository.save(
        shipment
    );

    ShipmentDeliveredEvent event =
        ShipmentDeliveredEvent.builder()
            .shipmentId(
                shipment.getId()
            )
            .orderId(
                shipment.getOrderId()
            )
            .deliveredAt(
                Instant.now()
            )
            .build();

    outboxPublisherService.publish(
        "SHIPMENT",
        shipment.getId().toString(),
        "shipment-delivered",
        event
    );
  }
}