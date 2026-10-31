package com.alves.picpay.controller;

import com.alves.picpay.dto.request.LoginRequest;
import com.alves.picpay.dto.request.RefreshTokenRequest;
import com.alves.picpay.dto.request.RegisterRequest;
import com.alves.picpay.dto.response.RefreshTokenResponse;
import com.alves.picpay.dto.response.UsuarioResponse;
import com.alves.picpay.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(RefreshTokenRequest req) {
        return ResponseEntity.ok(usuarioService.refresh(req));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UsuarioResponse>> listar(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAll(pageable));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest req) {
        usuarioService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<RefreshTokenResponse> login(@RequestBody @Valid LoginRequest req) {
        return ResponseEntity.ok().body(usuarioService.login(req));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> apagarPorToken(@RequestBody @Valid RefreshTokenRequest req) {
        usuarioService.apagarPorToken(req);
        return ResponseEntity.noContent().build();
    }
}
