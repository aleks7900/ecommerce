package com.aleks.shipping.service;

import com.aleks.shipping.entity.Shipment;
import com.aleks.shipping.entity.ShipmentStatus;
import com.aleks.shipping.repository.ShipmentRepository;
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
}