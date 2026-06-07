package com.aleks.analytics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import org.apache.avro.util.Utf8;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
public class KafkaConverterConfig {

  @Bean
  public RecordMessageConverter recordMessageConverter(
      ObjectMapper objectMapper) {

    return new RecordMessageConverter() {

      @Override
      public Message<?> toMessage(
          ConsumerRecord<?, ?> record,
          Acknowledgment acknowledgment,
          Consumer<?, ?> consumer,
          Type payloadType) {

        try {

          if (record.value() instanceof Utf8 utf8) {

            Object payload =
                objectMapper.readValue(
                    utf8.toString(),
                    objectMapper.constructType(payloadType)
                );

            return MessageBuilder
                .withPayload(payload)
                .build();
          }

          return MessageBuilder
              .withPayload(record.value())
              .build();

        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }

      @Override
      public ProducerRecord<?, ?> fromMessage(
          Message<?> message,
          String defaultTopic) {

        throw new UnsupportedOperationException();
      }
    };
  }
}
