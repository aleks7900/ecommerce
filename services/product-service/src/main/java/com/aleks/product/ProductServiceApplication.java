package com.aleks.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({
    "com.aleks.product.repository",
    "com.aleks.outbox.repository"
})
@EntityScan({
    "com.aleks.product.entity",
    "com.aleks.outbox.entity"
})
public class ProductServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(
        ProductServiceApplication.class,
        args
    );
  }
}
