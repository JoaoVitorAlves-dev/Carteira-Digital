package com.alves.picpay.controller;

import com.alves.picpay.dto.request.ChavePixRequest;
import com.alves.picpay.dto.response.ChavePixResponse;
import com.alves.picpay.service.ChavePixService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay/chavepix")
public class ChavePixController {

    private final ChavePixService chavePixService;

    public ChavePixController(ChavePixService chavePixService) {
        this.chavePixService = chavePixService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ChavePixResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(chavePixService.list(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<ChavePixResponse> create(@RequestBody @Valid ChavePixRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chavePixService.create(req, auth));
    }

    @PutMapping("/update")
    public ResponseEntity<ChavePixResponse> update(@RequestParam String id, ChavePixRequest req, Authentication auth) {
        return ResponseEntity.ok(chavePixService.update(id, req, auth));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String id, Authentication auth) {
        chavePixService.delete(id, auth);
        return ResponseEntity.noContent().build();
    }

}
