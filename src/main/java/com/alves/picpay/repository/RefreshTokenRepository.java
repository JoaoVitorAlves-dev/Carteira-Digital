package com.alves.picpay.repository;

import com.alves.picpay.domain.RefreshToken;
import com.alves.picpay.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    void deleteByUsuario(Usuario usuario);

    Optional<RefreshToken> findByToken(String token);

}
