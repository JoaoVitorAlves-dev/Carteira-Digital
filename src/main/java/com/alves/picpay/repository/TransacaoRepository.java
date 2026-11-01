package com.alves.picpay.repository;

import com.alves.picpay.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, String> {

    Optional<Transacao> findByIdAndOrigemId(String id, String origem);

}
