package com.alves.picpay.mapper;

import com.alves.picpay.domain.Endereco;
import com.alves.picpay.domain.Usuario;
import com.alves.picpay.dto.request.EnderecoRequest;
import com.alves.picpay.dto.response.EnderecoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnderecoMapper {

    public Endereco toEntity(EnderecoRequest req, Usuario usuario) {
        Endereco endereco = new Endereco();
        endereco.setRua(req.rua());
        endereco.setCep(req.cep());
        endereco.setCidade(req.cidade());
        endereco.setEstado(req.estado());
        endereco.setUsuario(usuario);
        return endereco;
    }

    public EnderecoResponse toDTO(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getId(), endereco.getUsuario(), endereco.getRua(), endereco.getCidade(),
                endereco.getEstado(), endereco.getCep()
        );
    }

}
