package com.aleks.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
    "com.aleks.payment.repository",
    "com.aleks.outbox.repository"
})
@EntityScan({
    "com.aleks.payment.entity",
    "com.aleks.outbox.entity"
})
public class PaymentServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(
        PaymentServiceApplication.class,
        args
    );
  }
}