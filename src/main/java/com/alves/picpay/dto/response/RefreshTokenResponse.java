package com.alves.picpay.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
