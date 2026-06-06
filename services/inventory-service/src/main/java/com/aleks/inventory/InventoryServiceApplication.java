package com.aleks.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.aleks.inventory",
        "com.aleks.outbox"
    }
)
@EnableJpaRepositories({
    "com.aleks.inventory.repository",
    "com.aleks.outbox.repository"
})
@EntityScan({
    "com.aleks.inventory.entity",
    "com.aleks.outbox.entity"
})
public class InventoryServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(
        InventoryServiceApplication.class,
        args
    );
  }
}
