package com.aleks.auth.dto.request;

public record ChangePasswordRequest(

    String currentPassword,

    String newPassword

) {
}
