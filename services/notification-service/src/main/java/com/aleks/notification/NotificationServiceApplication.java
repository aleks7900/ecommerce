package com.aleks.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
    "com.aleks.notification.repository",
    "com.aleks.outbox.repository"
})
@EntityScan({
    "com.aleks.notification.entity",
    "com.aleks.outbox.entity"
})
public class NotificationServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(
        NotificationServiceApplication.class,
        args
    );
  }
}