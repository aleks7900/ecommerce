package com.aleks.shipping.kafka;

import com.aleks.shared.event.OrderConfirmedEvent;
import com.aleks.shared.event.ShipmentCreatedEvent;
import com.aleks.shipping.entity.Shipment;
import com.aleks.shipping.event.ShippingEventPublisher;
import com.aleks.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class OrderConfirmedConsumer {

  private final ShippingService shippingService;

  private final ShippingEventPublisher producer;

  @KafkaListener(
      topics = "order-confirmed",
      groupId = "shipping-group"
  )
  public void consume(
      OrderConfirmedEvent event
  ) {

    Shipment shipment =
        shippingService.createShipment(

            Shipment.builder()
                .orderId(
                    event.orderId()
                )
                .address(
                    "Customer Address"
                )
                .build()
        );

    producer.publishShipmentCreated(

        ShipmentCreatedEvent.builder()
            .shipmentId(
                shipment.getId()
            )
            .orderId(
                shipment.getOrderId()
            )
            .createdAt(
                Instant.now()
            )
            .build()
    );
  }
}