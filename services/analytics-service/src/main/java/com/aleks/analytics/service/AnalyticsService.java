package com.aleks.analytics.service;

import com.aleks.analytics.dto.response.AnalyticsResponse;
import com.aleks.avro.OrderCreatedEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

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

  private BigDecimal revenue =
      BigDecimal.ZERO;

  private final RedisTemplate redisTemplate;

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
}