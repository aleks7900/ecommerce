package com.aleks.shipping.service;

import com.aleks.shipping.entity.Shipment;
import com.aleks.shipping.entity.ShipmentStatus;
import com.aleks.shipping.repository.ShipmentRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShipmentRepository repository;

  public Shipment createShipment(
      Shipment shipment
  ) {

    shipment.setStatus(
        ShipmentStatus.CREATED
    );

    return repository.save(
        shipment
    );
  }


  public Shipment markAsDelivered(
      UUID shipmentId
  ) {

    Optional<Shipment> byId = repository.findById(shipmentId);
    Shipment shipment = byId.get();
    shipment.setStatus(ShipmentStatus.DELIVERED);
    return repository.save(
        shipment
    );
  }
}