package com.aleks.payment.service;

import com.aleks.payment.entity.Payment;
import com.aleks.payment.entity.PaymentStatus;
import com.aleks.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;

  public Payment createPayment(
      UUID orderId,
      UUID buyerId,
      BigDecimal amount
  ) {

    Payment payment =
        Payment.builder()
            .orderId(orderId)
            .buyerId(buyerId)
            .amount(amount)
            .status(PaymentStatus.COMPLETED)
            .build();

    return paymentRepository.save(payment);
  }
}