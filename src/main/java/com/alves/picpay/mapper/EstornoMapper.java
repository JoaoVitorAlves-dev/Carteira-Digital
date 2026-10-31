package com.alves.picpay.mapper;

import com.alves.picpay.domain.Estorno;
import com.alves.picpay.domain.Transacao;
import com.alves.picpay.dto.request.EstornoRequest;
import com.alves.picpay.dto.response.EstornoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EstornoMapper {

    public Estorno toEntity(EstornoRequest req, Transacao transacao) {
        Estorno estorno = new Estorno();
        estorno.setMotivo(req.motivo());
        estorno.setStatus(req.status());
        estorno.setTransacao(transacao);
        estorno.setCriadoEm(req.criadoEm());
        return estorno;
    }

    public EstornoResponse toDTO(Estorno est) {
        return new EstornoResponse(
                est.getId(), est.getTransacao(), est.getMotivo(), est.getStatus(), est.getCriadoEm()
        );
    }

}
