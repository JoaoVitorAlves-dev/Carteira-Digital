package com.alves.picpay.domain;

import com.alves.picpay.domain.enums.TipoTransacao;
import com.alves.picpay.domain.enums.TransacaoStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origem_id", nullable = false)
    private Carteira origem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destino_id", nullable = false)
    private Carteira destino;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TipoTransacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "transacao_status", nullable = false)
    private TransacaoStatus status;

    @Column(name = "chave_destino", nullable = false)
    private String chaveDestino;

    @Column(name = "idempotency_key", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idempotencyKey;

    @Column(name = "criada_em", nullable = false)
    private LocalDateTime criadaEm;

    public Transacao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Carteira getOrigem() {
        return origem;
    }

    public void setOrigem(Carteira origem) {
        this.origem = origem;
    }

    public Carteira getDestino() {
        return destino;
    }

    public void setDestino(Carteira destino) {
        this.destino = destino;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public TransacaoStatus getStatus() {
        return status;
    }

    public void setStatus(TransacaoStatus status) {
        this.status = status;
    }

    public String getChaveDestino() {
        return chaveDestino;
    }

    public void setChaveDestino(String chaveDestino) {
        this.chaveDestino = chaveDestino;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public void setCriadaEm(LocalDateTime criadaEm) {
        this.criadaEm = criadaEm;
    }
}
