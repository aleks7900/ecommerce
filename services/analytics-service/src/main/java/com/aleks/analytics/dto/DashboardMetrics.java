package com.aleks.analytics.dto;

public record DashboardMetrics(

    Long totalOrders,

    Double totalRevenue,

    Long totalCustomers

) {
}