package com.alves.picpay.dto.response;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.enums.TipoTransacao;

import java.time.LocalDateTime;

public record TransacaoResponse(
        String id,
        Carteira origem,
        Carteira destino,
        TipoTransacao tipo,
        String chaveDestino,
        String idempotencyKey,
        LocalDateTime criadoEm
) {
}
