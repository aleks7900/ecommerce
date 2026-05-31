package com.aleks.payment.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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

  @Bean
  public TaskScheduler taskScheduler() {

    ThreadPoolTaskScheduler scheduler =
        new ThreadPoolTaskScheduler();

    scheduler.setPoolSize(2);
    scheduler.setThreadNamePrefix(
        "kafka-retry-"
    );

    scheduler.initialize();

    return scheduler;
  }
}

