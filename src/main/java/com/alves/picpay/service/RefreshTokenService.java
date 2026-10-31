package com.alves.picpay.service;

import com.alves.picpay.domain.RefreshToken;
import com.alves.picpay.domain.Usuario;
import com.alves.picpay.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.expiration}")
    private long expiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public RefreshToken criar(Usuario usuario) {
        refreshTokenRepository.deleteByUsuario(usuario);

        RefreshToken rt = new RefreshToken();
        rt.setUsuario(usuario);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiraEm(Instant.now().plus(expiration, ChronoUnit.MINUTES));
        return refreshTokenRepository.save(rt);
    }

    @Transactional
    public RefreshToken validarERotacionar(String refresh) {
        RefreshToken atual = refreshTokenRepository.findByToken(refresh)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (atual.getExpiraEm().isBefore(Instant.now())) {
            refreshTokenRepository.delete(atual);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return criar(atual.getUsuario());
    }

    @Transactional
    public void apagarPorToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }

}
