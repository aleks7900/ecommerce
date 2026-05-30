package com.aleks.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;

@Configuration
@EnableKafka
@EnableKafkaRetryTopic
public class KafkaConfig {

  @Bean
  public NewTopic paymentFailedDltTopic() {

    return new NewTopic(
        "inventory-reserved-dlt",
        3,
        (short) 1
    );
  }

}
