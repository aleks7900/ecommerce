package com.aleks.outbox.service;

import com.aleks.outbox.entity.OutboxEvent;
import com.aleks.outbox.entity.OutboxStatus;
import com.aleks.outbox.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxPublisherService {

  private final KafkaTemplate<String, String>
      kafkaTemplate;

  private final OutboxEventRepository repository;

  public void publish(
      OutboxEvent event
  ) {

    try {

      kafkaTemplate.send(
          event.getTopic(),
          event.getPayload()
      );

      event.setStatus(
          OutboxStatus.SENT
      );

      repository.save(event);

    } catch (Exception ex) {

      event.setStatus(
          OutboxStatus.FAILED
      );

      repository.save(event);

      log.error(
          "Failed to publish {}",
          event.getId(),
          ex
      );
    }
  }
}