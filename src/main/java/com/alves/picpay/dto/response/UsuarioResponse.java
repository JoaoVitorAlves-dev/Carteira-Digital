package com.alves.picpay.dto.response;

import com.alves.picpay.domain.enums.Role;

public record UsuarioResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        Role role
) {
}
