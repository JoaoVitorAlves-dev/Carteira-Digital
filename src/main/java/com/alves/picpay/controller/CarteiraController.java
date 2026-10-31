package com.alves.picpay.controller;

import com.alves.picpay.dto.request.CarteiraRequest;
import com.alves.picpay.dto.response.CarteiraResponse;
import com.alves.picpay.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay/carteira")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<CarteiraResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(carteiraService.listar(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<CarteiraResponse> create(@RequestBody @Valid CarteiraRequest req, Authentication auth) {
        return ResponseEntity.ok(carteiraService.criar(req, auth));
    }

    @PutMapping("/update")
    public ResponseEntity<CarteiraResponse> update(@RequestParam String id, @RequestBody @Valid CarteiraRequest req, Authentication auth) {
        return ResponseEntity.ok(carteiraService.atualizar(id, req, auth));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String id, Authentication auth) {
        carteiraService.apagar(id, auth);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
