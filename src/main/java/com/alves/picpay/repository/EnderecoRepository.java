package com.alves.picpay.repository;

import com.alves.picpay.domain.Endereco;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    Optional<Endereco> findByIdAndUsuarioId(String id, @NotNull Long aLong);
}
