package com.alves.picpay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ChavePixRequest(
        @NotBlank String carteiraId,
        @NotBlank String tipo,
        @NotNull @PositiveOrZero BigDecimal valor,
        @NotNull Boolean ativa
) {
}
