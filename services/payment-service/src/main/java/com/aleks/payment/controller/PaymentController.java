package com.aleks.payment.controller;

import com.aleks.payment.entity.Payment;
import com.aleks.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentRepository paymentRepository;

  @GetMapping
  public List<Payment> getAll() {

    return paymentRepository.findAll();
  }
}