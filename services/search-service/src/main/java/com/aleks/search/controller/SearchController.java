package com.aleks.search.controller;

import com.aleks.search.document.ProductDocument;
import com.aleks.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchService service;

  @GetMapping("/api/search")
  public List<ProductDocument> search(

      @RequestParam
      String query
  ) {

    return service.search(
        query
    );
  }
}