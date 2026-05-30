package com.aleks.analytics.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "analytics_snapshots")
public class AnalyticsSnapshot {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private Long productsCount;

  private Long ordersCount;

  private Long confirmedOrders;

  private Long notificationsSent;

  private BigDecimal revenue;

  private Instant createdAt;

  @PrePersist
  void prePersist() {
    createdAt = Instant.now();
  }
}