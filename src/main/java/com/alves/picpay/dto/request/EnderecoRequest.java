package com.alves.picpay.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoRequest(
        @NotNull Long usuarioId,
        @NotBlank String rua,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String cep
) {
}
