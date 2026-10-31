package com.alves.picpay.repository;

import com.alves.picpay.domain.Usuario;
import com.alves.picpay.dto.response.UsuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);

    Page<UsuarioResponse> findAllBy(Pageable pageable);
}
