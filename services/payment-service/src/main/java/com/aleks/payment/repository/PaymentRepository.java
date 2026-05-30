package com.aleks.payment.repository;

import com.aleks.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository
    extends JpaRepository<Payment, UUID> {
}