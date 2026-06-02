package com.aleks.outbox.repository;

import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository
    extends JpaRepository<OutboxEvent, UUID> {

  List<OutboxEvent> findByStatus(
      OutboxStatus status
  );
}