package com.aleks.product.controller;

import com.aleks.product.dto.request.CreateProductRequest;
import com.aleks.product.dto.request.UpdateProductRequest;
import com.aleks.product.dto.response.ProductResponse;
import com.aleks.product.entity.Product;
import com.aleks.product.mapper.ProductMapper;
import com.aleks.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  private final ProductMapper productMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse create(
      @RequestBody @Valid CreateProductRequest request
  ) {

    Product product =
        productService.create(request);

    return productMapper.toResponse(product);
  }

  @GetMapping("/{id}")
  public ProductResponse getById(
      @PathVariable UUID id
  ) {

    return productMapper.toResponse(
        productService.getById(id)
    );
  }

  @GetMapping
  public List<ProductResponse> getAll() {

    return productService.getAll()
        .stream()
        .map(productMapper::toResponse)
        .toList();
  }

  @PutMapping("/{id}")
  public ProductResponse update(
      @PathVariable UUID id,
      @RequestBody @Valid UpdateProductRequest request
  ) {

    return productMapper.toResponse(
        productService.update(id, request)
    );
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable UUID id
  ) {

    productService.delete(id);
  }
}