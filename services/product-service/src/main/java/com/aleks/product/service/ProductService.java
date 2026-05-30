package com.aleks.product.service;

import com.aleks.product.dto.request.CreateProductRequest;
import com.aleks.product.dto.request.UpdateProductRequest;
import com.aleks.product.entity.Product;
import com.aleks.product.exception.ProductNotFoundException;
import com.aleks.product.kafka.ProductEventProducer;
import com.aleks.product.repository.ProductRepository;
import com.aleks.shared.event.ProductCreatedEvent;
import com.aleks.shared.event.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  private final ProductEventProducer productEventProducer;

  public Product create(CreateProductRequest request) {

    Product product = Product.builder()
        .name(request.name())
        .description(request.description())
        .price(request.price())
        .sellerId(request.sellerId())
        .status(ProductStatus.ACTIVE)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    Product save = productRepository.save(product);
    productEventProducer.sendProductCreatedEvent(
        ProductCreatedEvent.builder()
            .productId(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .sellerId(product.getSellerId())
            .createdAt(product.getCreatedAt())
            .build());
    return save;
  }

  public Product getById(UUID id) {

    return productRepository.findById(id)
        .orElseThrow(() ->
            new ProductNotFoundException(
                "Product not found: " + id
            )
        );
  }

  public List<Product> getAll() {

    return productRepository.findAll();
  }

  public Product update(
      UUID id,
      UpdateProductRequest request
  ) {

    Product product = getById(id);

    product.setName(request.name());
    product.setDescription(request.description());
    product.setPrice(request.price());
    product.setStatus(request.status());
    product.setUpdatedAt(Instant.now());

    return productRepository.save(product);
  }

  public void delete(UUID id) {

    Product product = getById(id);

    productRepository.delete(product);
  }
}