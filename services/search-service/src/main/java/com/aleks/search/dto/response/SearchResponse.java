package com.aleks.search.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record SearchResponse(

    UUID id,

    String name,

    String description,

    BigDecimal price
) {
}