package com.aleks.analytics.controller;

import com.aleks.analytics.dto.DashboardMetrics;
import com.aleks.analytics.dto.RevenuePoint;
import com.aleks.analytics.dto.UserAnalytics;
import com.aleks.analytics.dto.response.AnalyticsResponse;
import com.aleks.analytics.service.AnalyticsService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {

  private final AnalyticsService analyticsService;

  @GetMapping("/api/analytics")
  public AnalyticsResponse analytics() {

    return analyticsService.getStats();
  }

  @GetMapping("/dashboard")
  public DashboardMetrics dashboard() {

    return analyticsService.getMetrics();
  }

  @GetMapping("/revenue")
  public List<RevenuePoint> revenue() {

    return analyticsService.revenueTrend();
  }

  @GetMapping("/users/{userId}")
  public UserAnalytics analytics(
      @PathVariable UUID userId
  ) {

    return analyticsService
        .getUserAnalytics(
            userId
        );
  }
}