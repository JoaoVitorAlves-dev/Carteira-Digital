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

    public EnderecoResponse atualizar(EnderecoRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Endereco entity = EnderecoMapper.toEntity(req, usuario);
        return EnderecoMapper.toDTO(enderecoRepository.save(entity));
    }

    public void apagarEndereco(EnderecoRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Endereco entity = EnderecoMapper.toEntity(req, usuario);
        enderecoRepository.delete(entity);
    }

}
