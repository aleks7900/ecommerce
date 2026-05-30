package com.aleks.auth.dto.response;

public record AuthResponse(

    String accessToken,

    String refreshToken
) {
}