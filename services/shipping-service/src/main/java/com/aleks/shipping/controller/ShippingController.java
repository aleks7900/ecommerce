package com.aleks.shipping.controller;

import com.aleks.shipping.dto.request.CreateShipmentRequest;
import com.aleks.shipping.entity.Shipment;
import com.aleks.shipping.repository.ShipmentRepository;
import com.aleks.shipping.service.ShippingService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShippingController {

  private final ShippingService shippingService;

  private final ShipmentRepository shipmentRepository;

  @PostMapping
  public Shipment createShipment(
      @RequestBody
      @Valid
      CreateShipmentRequest request
  ) {

    Shipment shipment =
        Shipment.builder()
            .orderId(
                request.orderId()
            )
            .address(
                request.address()
            )
            .build();

    return shippingService.createShipment(
        shipment
    );
  }

  @GetMapping
  public List<Shipment> findAll() {

    return shipmentRepository.findAll();
  }

  @PostMapping("/{shipmentId}/deliver")
  public void deliver(
      @PathVariable UUID shipmentId
  ) {

    shippingService.markAsDelivered(
        shipmentId
    );
  }
}