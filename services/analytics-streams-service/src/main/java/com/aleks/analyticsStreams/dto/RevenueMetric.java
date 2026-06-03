package com.aleks.analyticsStreams.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RevenueMetric(

    BigDecimal revenue
) {
}
