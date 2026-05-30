package com.aleks.auth.dto.response;

import java.time.Instant;

public record TokenResponse(

    String token,

    Instant expiresAt
) {
}
