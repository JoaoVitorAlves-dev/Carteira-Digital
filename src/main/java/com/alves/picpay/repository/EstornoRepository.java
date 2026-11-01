package com.alves.picpay.repository;

import com.alves.picpay.domain.Estorno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstornoRepository extends JpaRepository<Estorno, String> {

    Boolean existsByTransacaoId(String id);

}
