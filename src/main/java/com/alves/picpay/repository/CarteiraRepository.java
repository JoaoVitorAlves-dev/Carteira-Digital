package com.alves.picpay.repository;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.Estorno;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, String> {
    Optional<Carteira> findByIdAndUsuarioId(String id, @NotNull Long identity);

    Optional<Carteira> findByUsuarioEmail(String email);

}
