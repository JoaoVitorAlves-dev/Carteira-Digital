package com.alves.picpay.mapper;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.Usuario;
import com.alves.picpay.dto.request.CarteiraRequest;
import com.alves.picpay.dto.response.CarteiraResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CarteiraMapper {

    public Carteira toEntity(CarteiraRequest req, Usuario usuario) {
        Carteira carteira = new Carteira();
        carteira.setSaldo(req.saldo());
        carteira.setStatus(req.status());
        carteira.setUsuario(usuario);
        carteira.setVersion(req.version());
        return carteira;
    }

    public CarteiraResponse toDTO(Carteira carteira) {
        return new CarteiraResponse(
                carteira.getId(), carteira.getUsuario(), carteira.getSaldo(),
                carteira.getStatus(), carteira.getVersion()
        );
    }

}
