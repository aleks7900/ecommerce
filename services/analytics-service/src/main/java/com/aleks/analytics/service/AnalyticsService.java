package com.aleks.analytics.service;

import com.aleks.analytics.dto.DashboardMetrics;
import com.aleks.analytics.dto.RevenuePoint;
import com.aleks.analytics.dto.UserAnalytics;
import com.aleks.analytics.dto.response.AnalyticsResponse;
import com.aleks.analytics.repository.AnalyticsRepository;
import com.aleks.avro.OrderCreatedEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class AnalyticsService {

  private final AtomicLong products =
      new AtomicLong();

  private final AtomicLong orders =
      new AtomicLong();

  private final AtomicLong confirmedOrders =
      new AtomicLong();

  private final AtomicLong notifications =
      new AtomicLong();
  private final RedisTemplate redisTemplate;
  private final AnalyticsRepository repository;
  private BigDecimal revenue =
      BigDecimal.ZERO;

  public void productCreated() {
    products.incrementAndGet();
  }

  public void orderCreated(OrderCreatedEvent orderCreatedEvent) {

    redisTemplate.opsForZSet()
        .incrementScore(
            "top-products",
            orderCreatedEvent.getProductId(),
            1
        );

    orders.incrementAndGet();
  }

  public void orderConfirmed() {
    confirmedOrders.incrementAndGet();
  }

  public void notificationSent() {
    notifications.incrementAndGet();
  }

  public synchronized void paymentCompleted(
      BigDecimal amount
  ) {
    revenue = revenue.add(amount);
  }

  public AnalyticsResponse getStats() {

    return new AnalyticsResponse(
        products.get(),
        orders.get(),
        confirmedOrders.get(),
        notifications.get(),
        revenue
    );
  }

  public DashboardMetrics getMetrics() {

    return new DashboardMetrics(

        repository.countOrders(),

        repository.sumRevenue(),

        repository.countCustomers()
    );
  }

  public List<RevenuePoint> revenueTrend() {

    return repository
        .revenueTrend()

        .stream()

        .map(row ->

            new RevenuePoint(

                row[0].toString(),

                ((Number) row[1])
                    .doubleValue()
            )
        )

        .toList();
  }

  public UserAnalytics getUserAnalytics(
      UUID userId
  ) {

    Long ordersCount =
        repository.countByCustomerId(
            userId
        );

    BigDecimal totalSpent =
        Optional.ofNullable(

            repository.totalSpentByCustomerId(
                userId
            )

        ).orElse(
            BigDecimal.ZERO
        );

    return new UserAnalytics(

        ordersCount,

        totalSpent
    );
  }
}