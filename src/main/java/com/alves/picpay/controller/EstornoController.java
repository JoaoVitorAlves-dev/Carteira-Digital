package com.alves.picpay.controller;

import com.alves.picpay.dto.request.EstornoRequest;
import com.alves.picpay.dto.response.EstornoResponse;
import com.alves.picpay.service.EstornoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay/estorno")
public class EstornoController {

    private final EstornoService estornoService;

    public EstornoController(EstornoService estornoService) {
        this.estornoService = estornoService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<EstornoResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(estornoService.list(pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<EstornoResponse> create(@RequestBody @Valid EstornoRequest req, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estornoService.create(req, auth));
    }

    @PutMapping("/update")
    public ResponseEntity<EstornoResponse> update(@RequestParam String id, @RequestBody @Valid EstornoRequest req, Authentication auth) {
        return ResponseEntity.ok(estornoService.update(id, req, auth));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam String id, Authentication auth) {
        estornoService.delete(id, auth);
        return ResponseEntity.noContent().build();
    }

}
