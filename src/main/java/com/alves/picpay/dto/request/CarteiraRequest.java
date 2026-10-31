package com.alves.picpay.dto.request;

import com.alves.picpay.domain.enums.CarteiraStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CarteiraRequest(
        @NotNull Long usuarioId,
        @NotNull @PositiveOrZero BigDecimal saldo,
        @NotNull CarteiraStatus status,
        @NotBlank String version
) {
}
