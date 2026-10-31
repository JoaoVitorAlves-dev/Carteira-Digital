package com.alves.picpay.service;

import com.alves.picpay.domain.RefreshToken;
import com.alves.picpay.domain.Usuario;
import com.alves.picpay.domain.enums.Role;
import com.alves.picpay.dto.request.LoginRequest;
import com.alves.picpay.dto.request.RefreshTokenRequest;
import com.alves.picpay.dto.request.RegisterRequest;
import com.alves.picpay.dto.response.RefreshTokenResponse;
import com.alves.picpay.dto.response.UsuarioResponse;
import com.alves.picpay.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usuarioRepository, TokenService tokenService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Page<UsuarioResponse> findAll(Pageable pageable) {
        return usuarioRepository.findAllBy(pageable);
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest req) {
        RefreshToken refreshToken = refreshTokenService.validarERotacionar(req.refreshToken());

        String token = tokenService.gerarToken(refreshToken.getUsuario().getNome());

        return new RefreshTokenResponse(token, refreshToken.getToken());
    }

    public void register(RegisterRequest req) {
        if (usuarioRepository.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(req.username());
        usuario.setPassword(passwordEncoder.encode(req.password()));
        usuario.setCpf(req.cpf());
        usuario.setEmail(req.email());
        usuario.setRole(Role.CLIENTE);

        usuarioRepository.save(usuario);
    }

    public RefreshTokenResponse login(LoginRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));

        Usuario usuario = usuarioRepository.findByUsername(req.username()).orElseThrow();

        String token = tokenService.gerarToken(usuario.getNome());
        String refresh = refreshTokenService.criar(usuario).getToken();

        return new RefreshTokenResponse(token, refresh);
    }

    public void apagarPorToken(RefreshTokenRequest req) {
        refreshTokenService.apagarPorToken(req.accessToken());
    }

}
