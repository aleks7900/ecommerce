package com.aleks.auth.event;

import java.time.Instant;
import java.util.UUID;

public record UserLoggedInEvent(

    UUID userId,

    String email,

    Instant loginAt
) {
}
