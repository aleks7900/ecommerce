package com.aleks.product.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicsConfig {

  @Bean
  public NewTopic productCreatedTopic() {

    return new NewTopic(
        "product-created",
        3,
        (short) 1
    );
  }

  @Bean
  public NewTopic productUpdatedTopic() {

    return new NewTopic(
        "product-updated",
        3,
        (short) 1
    );
  }

  @Bean
  public NewTopic productDeletedTopic() {

    return new NewTopic(
        "product-deleted",
        3,
        (short) 1
    );
  }
}
