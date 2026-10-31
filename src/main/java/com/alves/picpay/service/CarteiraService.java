package com.alves.picpay.service;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.Usuario;
import com.alves.picpay.dto.request.CarteiraRequest;
import com.alves.picpay.dto.response.CarteiraResponse;
import com.alves.picpay.mapper.CarteiraMapper;
import com.alves.picpay.mapper.EnderecoMapper;
import com.alves.picpay.repository.CarteiraRepository;
import com.alves.picpay.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;
    private final UsuarioRepository usuarioRepository;

    public CarteiraService(CarteiraRepository carteiraRepository, UsuarioRepository usuarioRepository) {
        this.carteiraRepository = carteiraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<CarteiraResponse> listar(Pageable pageable) {
        return carteiraRepository.findAll(pageable).map(CarteiraMapper::toDTO);
    }

    public CarteiraResponse criar(CarteiraRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Carteira entity = CarteiraMapper.toEntity(req, usuario);

        return CarteiraMapper.toDTO(carteiraRepository.save(entity));
    }

    public CarteiraResponse atualizar(CarteiraRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Carteira entity = CarteiraMapper.toEntity(req, usuario);

        return CarteiraMapper.toDTO(carteiraRepository.save(entity));
    }

    public void apagar(CarteiraRequest req, Authentication auth) {
        Usuario usuario = usuarioRepository.findById(req.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!usuario.getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Carteira entity = CarteiraMapper.toEntity(req, usuario);
        carteiraRepository.delete(entity);
    }

}
