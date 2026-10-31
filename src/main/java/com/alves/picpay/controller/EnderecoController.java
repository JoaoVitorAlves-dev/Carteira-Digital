package com.alves.picpay.controller;


import com.alves.picpay.dto.request.EnderecoRequest;
import com.alves.picpay.dto.response.EnderecoResponse;
import com.alves.picpay.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<EnderecoResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(enderecoService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<EnderecoResponse> create(@RequestBody @Valid EnderecoRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.criar(req, auth));
    }

    @PutMapping("/update")
    public ResponseEntity<EnderecoResponse> update(@RequestBody @Valid EnderecoRequest req, Authentication auth) {
        return ResponseEntity.ok(enderecoService.atualizar(req, auth));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<EnderecoResponse> delete(@RequestBody @Valid EnderecoRequest req, Authentication auth) {
        enderecoService.apagarEndereco(req, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
