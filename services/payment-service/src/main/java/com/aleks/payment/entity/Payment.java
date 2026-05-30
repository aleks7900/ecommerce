package com.aleks.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID orderId;

  private UUID buyerId;

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  private Instant createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = Instant.now();
  }
}