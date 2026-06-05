package com.aleks.outbox.scheduler;

import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.aleks.outbox.service.OutboxPublisherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

  private final OutboxEventRepository outboxEventRepository;

  private final ObjectMapper objectMapper;

  private final OutboxPublisherService outboxPublisherService;

  @Scheduled(
      fixedDelay = 5000
  )
  public void processOutbox() {

    outboxEventRepository.findByStatus(
        OutboxStatus.NEW
    ).forEach(outboxEvent ->
        {
          try {
            outboxPublisherService.publish(
                OutboxEvent.builder()
                    .aggregateType(
                        outboxEvent.getAggregateType()
                    )
                    .aggregateId(
                        outboxEvent.getAggregateId()
                    )
                    .eventType(
                        outboxEvent.getEventType()
                    )
                    .payload(
                        objectMapper.writeValueAsString(
                            outboxEvent
                        )
                    )
                    .createdAt(
                        Instant.now()
                    )
                    .build());
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }
}