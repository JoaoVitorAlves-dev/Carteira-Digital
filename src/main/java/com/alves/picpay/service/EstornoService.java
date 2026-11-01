package com.alves.picpay.service;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.Estorno;
import com.alves.picpay.domain.Transacao;
import com.alves.picpay.dto.request.EstornoRequest;
import com.alves.picpay.dto.response.EstornoResponse;
import com.alves.picpay.mapper.EstornoMapper;
import com.alves.picpay.repository.CarteiraRepository;
import com.alves.picpay.repository.EstornoRepository;
import com.alves.picpay.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EstornoService {

    private final EstornoRepository estornoRepository;
    private final CarteiraRepository carteiraRepository;
    private final TransacaoRepository transacaoRepository;

    public EstornoService(EstornoRepository estornoRepository, CarteiraRepository carteiraRepository, TransacaoRepository transacaoRepository) {
        this.estornoRepository = estornoRepository;
        this.carteiraRepository = carteiraRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public Page<EstornoResponse> list(Pageable pageable) {
        return estornoRepository.findAll(pageable).map(EstornoMapper::toDTO);
    }

    public EstornoResponse create(EstornoRequest req, Authentication auth) {
        Carteira carteira = carteiraRepository.findByUsuarioEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transacao transacao = transacaoRepository.findByIdAndOrigemId(req.transacaoId(), carteira.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Estorno entity = EstornoMapper.toEntity(req, transacao);

        if (estornoRepository.existsByTransacaoId(transacao.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        return EstornoMapper.toDTO(estornoRepository.save(entity));
    }

    public EstornoResponse update(String id, EstornoRequest req, Authentication auth) {
        Carteira carteira = carteiraRepository.findByUsuarioEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Transacao transacao = transacaoRepository.findByIdAndOrigemId(req.transacaoId(), carteira.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Estorno estorno = estornoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        estorno.setTransacao(transacao);
        estorno.setStatus(req.status());
        estorno.setMotivo(req.motivo());
        estorno.setCriadoEm(req.criadoEm());

        return EstornoMapper.toDTO(estornoRepository.save(estorno));
    }

    public void delete(String id, Authentication auth) {
        Estorno estorno = estornoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Carteira carteira = carteiraRepository.findByUsuarioEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!estorno.getTransacao().getOrigem().equals(carteira)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        estornoRepository.delete(estorno);
    }

}
