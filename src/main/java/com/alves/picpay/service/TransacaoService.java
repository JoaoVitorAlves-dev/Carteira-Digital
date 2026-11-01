package com.alves.picpay.service;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.Transacao;
import com.alves.picpay.dto.request.TransacaoRequest;
import com.alves.picpay.dto.response.TransacaoResponse;
import com.alves.picpay.mapper.TransacaoMapper;
import com.alves.picpay.repository.CarteiraRepository;
import com.alves.picpay.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CarteiraRepository carteiraRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, CarteiraRepository carteiraRepository) {
        this.transacaoRepository = transacaoRepository;
        this.carteiraRepository = carteiraRepository;
    }

    public Page<TransacaoResponse> list(Pageable pageable) {
        return transacaoRepository.findAll(pageable).map(TransacaoMapper::toDTO);
    }

    public TransacaoResponse create(TransacaoRequest req, Authentication auth) {
        Carteira origem = carteiraRepository.findById(req.carteiraOrigemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Carteira destino = carteiraRepository.findById(req.carteiraDestinoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transacao entity = TransacaoMapper.toEntity(req, destino, origem);

        Transacao transacao = transacaoRepository.findByIdAndOrigemId(entity.getId(), auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return TransacaoMapper.toDTO(transacaoRepository.save(transacao));
    }

    public TransacaoResponse update(String id, TransacaoRequest req, Authentication auth) {
        Carteira origem = carteiraRepository.findByUsuarioEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!origem.getId().equals(req.carteiraOrigemId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Carteira destino = carteiraRepository.findById(req.carteiraDestinoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transacao transacao = transacaoRepository.findByIdAndOrigemId(id, origem.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transacao entity = TransacaoMapper.toEntity(req, destino, origem);

        transacao.setTipo(entity.getTipo());
        transacao.setStatus(entity.getStatus());
        transacao.setOrigem(entity.getOrigem());
        transacao.setDestino(entity.getDestino());
        transacao.setChaveDestino(entity.getChaveDestino());

        return TransacaoMapper.toDTO(transacaoRepository.save(transacao));
    }

    public void delete(String id, Authentication auth) {
        Carteira origem = carteiraRepository.findByUsuarioEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!transacao.getOrigem().equals(origem)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        transacaoRepository.delete(transacao);
    }

}
