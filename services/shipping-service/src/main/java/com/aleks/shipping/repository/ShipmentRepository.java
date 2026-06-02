package com.aleks.shipping.repository;

import com.aleks.shipping.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShipmentRepository
    extends JpaRepository<Shipment, UUID> {
}