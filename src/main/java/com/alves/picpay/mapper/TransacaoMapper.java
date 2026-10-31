package com.alves.picpay.mapper;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.Transacao;
import com.alves.picpay.dto.request.TransacaoRequest;
import com.alves.picpay.dto.response.TransacaoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransacaoMapper {

    public Transacao toEntity(TransacaoRequest req, Carteira destino, Carteira origem) {
        Transacao transacao = new Transacao();
        transacao.setCriadaEm(req.criadoEm());
        transacao.setChaveDestino(req.chaveDestino());
        transacao.setIdempotencyKey(req.idempotencyKey());
        transacao.setDestino(destino);
        transacao.setOrigem(origem);
        transacao.setStatus(req.status());
        transacao.setTipo(req.tipoTransacao());
        return transacao;
    }

    public TransacaoResponse toDTO(Transacao tr) {
        return new TransacaoResponse(
                tr.getId(), tr.getOrigem(), tr.getDestino(), tr.getTipo(), tr.getChaveDestino(), tr.getIdempotencyKey(), tr.getCriadaEm()
        );
    }

}
