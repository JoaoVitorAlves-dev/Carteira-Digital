package com.alves.picpay.mapper;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.ChavePix;
import com.alves.picpay.dto.request.ChavePixRequest;
import com.alves.picpay.dto.response.ChavePixResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChavePixMapper {

    public ChavePix toEntity(ChavePixRequest req, Carteira carteira) {
        ChavePix chavePix = new ChavePix();
        chavePix.setTipo(req.tipo());
        chavePix.setCarteira(carteira);
        chavePix.setAtiva(req.ativa());
        chavePix.setValor(req.valor());
        return chavePix;
    }

    public ChavePixResponse toDTO(ChavePix ch) {
        return new ChavePixResponse(
                ch.getId(), ch.getCarteira(), ch.getTipo(),
                ch.getValor(), ch.getAtiva()
        );
    }

}
