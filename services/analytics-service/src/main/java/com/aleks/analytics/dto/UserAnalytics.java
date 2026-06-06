package com.aleks.analytics.dto;

import java.math.BigDecimal;

public record UserAnalytics(

    Long ordersCount,

    BigDecimal totalSpent

) {
}
