package com.alves.picpay.dto.response;

import com.alves.picpay.domain.Transacao;
import com.alves.picpay.domain.enums.EstornoStatus;

import java.time.LocalDateTime;

public record EstornoResponse(
        String id,
        Transacao transacao,
        String motivo,
        EstornoStatus status,
        LocalDateTime criadoEm
) {
}
