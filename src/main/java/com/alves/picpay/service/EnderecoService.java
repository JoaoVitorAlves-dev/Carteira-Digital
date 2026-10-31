package com.alves.picpay.service;

import com.alves.picpay.domain.Endereco;
import com.alves.picpay.domain.Usuario;
import com.alves.picpay.dto.request.EnderecoRequest;
import com.alves.picpay.dto.response.EnderecoResponse;
import com.alves.picpay.mapper.EnderecoMapper;
import com.alves.picpay.repository.EnderecoRepository;
import com.alves.picpay.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<EnderecoResponse> listar(Pageable pageable) {
        return enderecoRepository.findAll(pageable).map(EnderecoMapper::toDTO);
    }

    public EnderecoResponse criar(EnderecoRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Endereco entity = EnderecoMapper.toEntity(req, usuario);
        return EnderecoMapper.toDTO(enderecoRepository.save(entity));
    }

    public EnderecoResponse atualizar(String id, EnderecoRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Endereco endereco = enderecoRepository.findByIdAndUsuarioId(id, req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        endereco.setUsuario(usuario);
        endereco.setEstado(req.estado());
        endereco.setCidade(req.cidade());
        endereco.setCep(req.cep());
        endereco.setRua(req.rua());

        return EnderecoMapper.toDTO(enderecoRepository.save(endereco));
    }

    public void apagarEndereco(String id, Authentication auth) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!endereco.getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        enderecoRepository.delete(endereco);
    }

}
