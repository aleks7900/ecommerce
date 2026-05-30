package com.aleks.analytics.controller;

import com.aleks.analytics.dto.response.AnalyticsResponse;
import com.aleks.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnalyticsController {

  private final AnalyticsService analyticsService;

  @GetMapping("/api/analytics")
  public AnalyticsResponse analytics() {

    return analyticsService.getStats();
  }
}