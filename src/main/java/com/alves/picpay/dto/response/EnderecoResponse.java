package com.alves.picpay.dto.response;

import com.alves.picpay.domain.Usuario;

public record EnderecoResponse(
        String id,
        Usuario usuario,
        String rua,
        String cidade,
        String estado,
        String cep
) {
}
