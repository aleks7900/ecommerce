package com.aleks.outbox.service;

import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutboxPublisherService {

  private final OutboxEventRepository repository;

  private final ObjectMapper objectMapper;

  public void publish(

      String aggregateType,

      String aggregateId,

      String eventType,

      Object event
  ) {

    try {

      repository.save(

          OutboxEvent.builder()

              .aggregateType(
                  aggregateType
              )

              .aggregateId(
                  aggregateId
              )

              .eventType(
                  eventType
              )

              .payload(
                  objectMapper.writeValueAsString(
                      event
                  )
              )

              .createdAt(
                  Instant.now()
              )

              .build()
      );

    } catch (Exception ex) {

      throw new RuntimeException(
          "Failed to save outbox event",
          ex
      );
    }
  }
}