package com.aleks.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.aleks.order",
    "com.aleks.outbox"
})
@EnableJpaRepositories({
    "com.aleks.order.repository",
    "com.aleks.outbox.repository"
})
@EntityScan({
    "com.aleks.order.entity",
    "com.aleks.outbox.entity"
})
@EnableFeignClients
public class OrderServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(
        OrderServiceApplication.class,
        args
    );
  }
}