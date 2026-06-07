package com.aleks.outbox.service;

import com.aleks.avro.util.AvroJsonUtils;
import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxPublisherService {

  private final OutboxEventRepository outboxEventRepository;

  private final ObjectMapper objectMapper;

  public void publish(

      String aggregateType,

      String aggregateId,

      String eventType,

      Object event
  ) {

    JsonNode payload;
    try {

      if (event instanceof SpecificRecord record) {

        payload =
            objectMapper.readTree(
                AvroJsonUtils.toJson(record)
            );

      } else {

        payload =
            objectMapper.valueToTree(event);
      }

      outboxEventRepository.save(

          OutboxEvent.builder()

              .aggregateType(
                  aggregateType
              )
              .topic(eventType)
              .aggregateId(
                  aggregateId
              )

              .eventType(
                  eventType
              )
              .status(OutboxStatus.NEW)
              .payload(
                  payload
              )

              .createdAt(
                  Instant.now()
              )

              .build()
      );

    } catch (Exception ex) {
      log.error(
          "Failed to save outbox event",
          ex
      );
      ex.printStackTrace();
      throw new RuntimeException(
          "Failed to save outbox event",
          ex
      );
    }
  }

  public void publish(OutboxEvent outboxEvent) {
    outboxEventRepository.save(outboxEvent);
  }
}