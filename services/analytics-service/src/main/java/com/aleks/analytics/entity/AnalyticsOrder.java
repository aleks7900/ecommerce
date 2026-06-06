package com.aleks.analytics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "analytics_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsOrder {

  @Id
  private UUID orderId;

  private UUID customerId;

  private BigDecimal totalPrice;

  private Instant createdAt;
}
