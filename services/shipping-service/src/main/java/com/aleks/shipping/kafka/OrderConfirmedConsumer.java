package com.aleks.shipping.kafka;

import com.aleks.avro.OrderConfirmedEvent;
import com.aleks.avro.ShipmentCreatedEvent;
import com.aleks.shipping.entity.Shipment;
import com.aleks.shipping.service.ShippingService;
import java.util.UUID;
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
                    UUID.fromString(event.getOrderId())
                )
                .address(
                    "Customer Address"
                )
                .build()
        );

    producer.publishShipmentCreated(

        ShipmentCreatedEvent.newBuilder()
            .setShipmentId(
                String.valueOf(shipment.getId())
            )
            .setOrderId(
                String.valueOf(shipment.getOrderId())
            )
            .setCreatedAt(
                String.valueOf(Instant.now())
            )
            .build()
    );
  }
}