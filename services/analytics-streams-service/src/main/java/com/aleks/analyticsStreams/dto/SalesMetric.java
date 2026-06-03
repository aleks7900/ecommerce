package com.aleks.analyticsStreams.dto;

import lombok.Builder;

@Builder
public record SalesMetric(

    String productId,

    Long totalOrders
) {
}