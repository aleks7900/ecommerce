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

  private final OutboxEventRepository outboxEventRepository;

  private final OutboxPublisherService outboxPublisherService;

  @Scheduled(
      fixedDelay = 5000
  )
  public void processOutbox() {

    outboxEventRepository.findByStatus(
        OutboxStatus.NEW
    ).forEach(
        outboxPublisherService::publish
    );
  }
}