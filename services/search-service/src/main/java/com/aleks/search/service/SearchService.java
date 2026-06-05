package com.aleks.search.service;

import com.aleks.search.document.ProductDocument;
import com.aleks.search.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

  private final ProductSearchRepository repository;

  @Cacheable(
      value = "product-search",
      key = "#query"
  )
  public List<ProductDocument> search(
      String query
  ) {

    return repository
        .findByNameContainingIgnoreCase(
            query
        );
  }

  public ProductDocument save(
      ProductDocument document
  ) {

    return repository.save(
        document
    );
  }
}