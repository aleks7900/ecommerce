package com.aleks.product.repository;

import com.aleks.product.entity.Product;
import com.aleks.shared.event.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository
    extends JpaRepository<Product, UUID> {

  List<Product> findByStatus(ProductStatus status);

  List<Product> findBySellerId(UUID sellerId);
}