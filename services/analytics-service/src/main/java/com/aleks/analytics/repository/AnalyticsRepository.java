package com.aleks.analytics.repository;

import com.aleks.analytics.entity.AnalyticsSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnalyticsRepository
    extends JpaRepository<AnalyticsSnapshot, UUID> {
}