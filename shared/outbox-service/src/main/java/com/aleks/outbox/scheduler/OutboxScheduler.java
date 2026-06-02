package com.aleks.outbox.scheduler;

import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.aleks.outbox.service.OutboxPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

  private final OutboxEventRepository repository;

  private final OutboxPublisherService publisher;

  @Scheduled(
      fixedDelay = 5000
  )
  public void processOutbox() {

    repository.findByStatus(
        OutboxStatus.NEW
    ).forEach(
        publisher::publish
    );
  }
}