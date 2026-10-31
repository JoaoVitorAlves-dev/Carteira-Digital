package com.alves.picpay.service;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.ChavePix;
import com.alves.picpay.dto.request.ChavePixRequest;
import com.alves.picpay.dto.response.ChavePixResponse;
import com.alves.picpay.mapper.ChavePixMapper;
import com.alves.picpay.repository.CarteiraRepository;
import com.alves.picpay.repository.ChavePixRepository;
import com.alves.picpay.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ChavePixService {

    private final ChavePixRepository chavePixRepository;
    private final CarteiraRepository carteiraRepository;

    public ChavePixService(ChavePixRepository chavePixRepository, CarteiraRepository carteiraRepository) {
        this.chavePixRepository = chavePixRepository;
        this.carteiraRepository = carteiraRepository;
    }

    public Page<ChavePixResponse> list(Pageable pageable) {
        return chavePixRepository.findAll(pageable).map(ChavePixMapper::toDTO);
    }

    public ChavePixResponse create(ChavePixRequest req, Authentication auth) {
        Carteira carteira = carteiraRepository.findById(req.carteiraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));

        if (!carteira.getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (req.ativa() == false) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        ChavePix entity = ChavePixMapper.toEntity(req, carteira);
        return ChavePixMapper.toDTO(entity);
    }

    public ChavePixResponse update(String id,ChavePixRequest req, Authentication auth) {
        Carteira carteira = carteiraRepository.findById(req.carteiraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!carteira.getUsuario().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (req.ativa() == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ChavePix chavePix = chavePixRepository.findByIdAndCarteiraId(id, carteira.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        chavePix.setValor(req.valor());
        chavePix.setAtiva(true);
        chavePix.setCarteira(carteira);
        chavePix.setTipo(req.tipo());

        return ChavePixMapper.toDTO(chavePixRepository.save(chavePix));
    }

}
