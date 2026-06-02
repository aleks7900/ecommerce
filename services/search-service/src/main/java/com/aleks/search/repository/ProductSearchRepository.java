package com.aleks.search.repository;

import com.aleks.search.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository
    extends ElasticsearchRepository<ProductDocument, UUID> {

  List<ProductDocument>
  findByNameContainingIgnoreCase(
      String keyword
  );
}