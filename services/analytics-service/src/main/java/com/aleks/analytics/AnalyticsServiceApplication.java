package com.aleks.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class AnalyticsServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(
        AnalyticsServiceApplication.class,
        args
    );
  }
}