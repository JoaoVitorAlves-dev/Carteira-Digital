package com.alves.picpay.dto.response;

import com.alves.picpay.domain.Carteira;

import java.math.BigDecimal;

public record ChavePixResponse(
        String id,
        Carteira carteira,
        String tipo,
        BigDecimal valor,
        Boolean ativa
) {
}
