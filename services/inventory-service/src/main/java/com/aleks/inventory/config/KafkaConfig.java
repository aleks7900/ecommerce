package com.aleks.inventory.config;

import com.aleks.avro.OrderCreatedEvent;
import com.aleks.avro.ProductCreatedEvent;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;


@Configuration
@EnableKafka
public class KafkaConfig {

  @Bean
  public ConsumerFactory<String, OrderCreatedEvent> orderConsumerFactory() {

    return new DefaultKafkaConsumerFactory<>(
        props()
    );
  }

  @Bean
  public ConsumerFactory<String, ProductCreatedEvent> productConsumerFactory() {

    return new DefaultKafkaConsumerFactory<>(
        props()
    );
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent>
  orderKafkaListenerContainerFactory(
      RecordMessageConverter converter) {

    ConcurrentKafkaListenerContainerFactory<
        String,
        OrderCreatedEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(orderConsumerFactory());

    factory.setRecordMessageConverter(converter);

    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent>
  productKafkaListenerContainerFactory(
      RecordMessageConverter converter) {

    ConcurrentKafkaListenerContainerFactory<
        String,
        ProductCreatedEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(productConsumerFactory());

    factory.setRecordMessageConverter(converter);

    return factory;
  }

  private Map<String, Object> props() {

    Map<String, Object> props = new HashMap<>();

    props.put(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        "localhost:29092"
    );

    props.put(
        ConsumerConfig.GROUP_ID_CONFIG,
        "analytics-group"
    );

    props.put(
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
        "earliest"
    );

    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class
    );

    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        KafkaAvroDeserializer.class
    );

    props.put(
        "schema.registry.url",
        "http://localhost:8087"
    );

    props.put(
        "specific.avro.reader",
        true
    );

    return props;
  }
}