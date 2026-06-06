package com.aleks.analytics.repository;

import com.aleks.analytics.entity.AnalyticsOrder;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository
    extends JpaRepository<AnalyticsOrder, UUID> {

  @Query("""
        select count(a)
        from AnalyticsOrder a
    """)
  Long countOrders();

  @Query("""
        select coalesce(sum(a.totalPrice), 0)
        from AnalyticsOrder a
    """)
  Double sumRevenue();

  @Query("""
        select count(distinct a.customerId)
        from AnalyticsOrder a
    """)
  Long countCustomers();

  @Query("""
    select
        DATE(a.createdAt),
        sum(a.totalPrice)
    from AnalyticsOrder a
    group by DATE(a.createdAt)
    order by DATE(a.createdAt)
""")
  List<Object[]> revenueTrend();

  Long countByCustomerId(
      UUID customerId
  );

  @Query("""
        select coalesce(
            sum(a.totalPrice),
            0
        )
        from AnalyticsOrder a
        where a.customerId = :customerId
    """)
  BigDecimal totalSpentByCustomerId(
      @Param("customerId")
      UUID customerId
  );
}