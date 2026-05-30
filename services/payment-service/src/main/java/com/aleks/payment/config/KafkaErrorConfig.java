package com.aleks.payment.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaErrorConfig {

  @Bean
  public DefaultErrorHandler errorHandler(
      KafkaTemplate<Object, Object> kafkaTemplate
  ) {

    DeadLetterPublishingRecoverer recoverer =
        new DeadLetterPublishingRecoverer(
            kafkaTemplate,
            (record, exception) -> {

              log.error(
                  "Sending message to DLQ. topic={}, key={}",
                  record.topic(),
                  record.key(),
                  exception
              );

              return new org.apache.kafka.common.TopicPartition(
                  record.topic() + "-dlt",
                  record.partition()
              );
            }
        );

    return new DefaultErrorHandler(
        recoverer,
        new FixedBackOff(
            3000L,
            3L
        )
    );
  }
}