package com.aleks.auth.dto.request;

public record UpdateProfileRequest(

    String firstName,

    String lastName,

    String avatarUrl

) {
}
