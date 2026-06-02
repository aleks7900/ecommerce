package com.aleks.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
    "com.aleks.shipping.repository",
    "com.aleks.outbox.repository"
})
@EntityScan({
    "com.aleks.shipping.entity",
    "com.aleks.outbox.entity"
})
public class ShippingServiceApplication {

  public static void main(
      String[] args
  ) {

    SpringApplication.run(
        ShippingServiceApplication.class,
        args
    );
  }
}