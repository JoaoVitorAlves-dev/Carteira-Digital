package com.alves.picpay.controller;

import com.alves.picpay.dto.request.TransacaoRequest;
import com.alves.picpay.dto.response.TransacaoResponse;
import com.alves.picpay.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TransacaoResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(transacaoService.list(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<TransacaoResponse> create(@RequestBody @Valid TransacaoRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.create(req, auth));
    }

    @PutMapping("/update")
    public ResponseEntity<TransacaoResponse> update(@RequestParam String id, @RequestBody @Valid TransacaoRequest req, Authentication auth) {
        return ResponseEntity.ok(transacaoService.update(id, req, auth));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String id, Authentication auth) {
        transacaoService.delete(id, auth);
        return ResponseEntity.noContent().build();
    }

}
