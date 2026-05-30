package com.aleks.analytics.dto.response;

import java.math.BigDecimal;

public record AnalyticsResponse(

    Long productsCount,

    Long ordersCount,

    Long confirmedOrders,

    Long notificationsSent,

    BigDecimal revenue
) {
}