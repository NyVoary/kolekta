package com.lokaly.controller;

import com.lokaly.dto.fournisseur.FournisseurRequest;
import com.lokaly.dto.fournisseur.FournisseurResponse;
import com.lokaly.service.FournisseurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService fournisseurService;

    @PostMapping
    public ResponseEntity<FournisseurResponse> creer(@Valid @RequestBody FournisseurRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurService.creer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurResponse> modifier(@PathVariable Long id, @Valid @RequestBody FournisseurRequest request) {
        return ResponseEntity.ok(fournisseurService.modifier(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<FournisseurResponse>> listerActifs() {
        return ResponseEntity.ok(fournisseurService.listerActifs());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactiver(@PathVariable Long id) {
        fournisseurService.desactiver(id);
        return ResponseEntity.noContent().build();
    }
}
