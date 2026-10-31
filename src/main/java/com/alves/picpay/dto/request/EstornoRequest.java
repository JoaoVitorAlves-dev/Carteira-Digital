package com.alves.picpay.dto.request;

import com.alves.picpay.domain.enums.EstornoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EstornoRequest(
        @NotBlank String transacaoId,
        @NotBlank String motivo,
        @NotNull EstornoStatus status,
        @NotNull LocalDateTime criadoEm
) {
}
