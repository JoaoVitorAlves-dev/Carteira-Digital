package com.alves.picpay.repository;

import com.alves.picpay.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);

}
