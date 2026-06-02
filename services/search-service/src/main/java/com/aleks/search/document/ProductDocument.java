package com.aleks.search.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
public class ProductDocument {

  @Id
  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;

  private String status;

  private UUID sellerId;
}