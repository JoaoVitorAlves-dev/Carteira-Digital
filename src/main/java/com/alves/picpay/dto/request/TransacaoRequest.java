package com.alves.picpay.dto.request;

import com.alves.picpay.domain.enums.TransacaoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransacaoRequest(
        @NotBlank String carteiraOrigemId,
        @NotBlank String carteiraDestinoId,
        @NotNull TransacaoStatus status,
        @NotBlank String chaveDestino,
        @NotBlank String idempotencyKey,
        @NotNull LocalDateTime criadoEm
) {
}
