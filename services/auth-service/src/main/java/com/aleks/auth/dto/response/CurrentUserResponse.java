package com.aleks.auth.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record CurrentUserResponse(

    UUID id,

    String email,

    String firstName,

    String lastName,

    Set<String> roles,

    Instant createdAt

) {
}
