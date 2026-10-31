package com.alves.picpay.dto.response;

import com.alves.picpay.domain.Usuario;
import com.alves.picpay.domain.enums.CarteiraStatus;

import java.math.BigDecimal;

public record CarteiraResponse(
        String id,
        Usuario usuario,
        BigDecimal saldo,
        CarteiraStatus status,
        String version
) {
}
