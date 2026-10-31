package com.alves.picpay.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank String accessToken,
        @NotBlank String refreshToken
) {
}
