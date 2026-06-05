package com.aleks.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.aleks.inventory",
    "com.aleks.outbox"
})
public class InventoryServiceApplication {

  public static void main(String[] args) {

    SpringApplication.run(
        InventoryServiceApplication.class,
        args
    );
  }
}
