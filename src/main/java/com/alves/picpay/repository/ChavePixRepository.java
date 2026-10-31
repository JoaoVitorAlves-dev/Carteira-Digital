package com.alves.picpay.repository;

import com.alves.picpay.domain.Carteira;
import com.alves.picpay.domain.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, String> {
    Optional<ChavePix> findByIdAndCarteiraId(String id, String identity);
}
