package com.aleks.product.mapper;

import com.aleks.product.dto.response.ProductResponse;
import com.aleks.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductResponse toResponse(
      Product product
  ) {

    return new ProductResponse(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStatus(),
        product.getSellerId(),
        product.getCreatedAt(),
        product.getUpdatedAt()
    );
  }
}