package com.aleks.auth.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserProfileResponse(

    UUID id,

    String email,

    String firstName,

    String lastName,

    String avatarUrl,

    Set<String> roles,

    Instant createdAt,

    Instant lastLogin,

    Long ordersCount,

    BigDecimal totalSpent

) {
}
