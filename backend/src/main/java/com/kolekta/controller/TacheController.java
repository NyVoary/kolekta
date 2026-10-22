package com.kolekta.controller;

import com.kolekta.dto.tache.TacheRequest;
import com.kolekta.dto.tache.TacheResponse;
import com.kolekta.service.TacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@RequiredArgsConstructor
public class TacheController {

    private final TacheService tacheService;

    @PostMapping
    public ResponseEntity<TacheResponse> creer(@Valid @RequestBody TacheRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tacheService.creer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacheResponse> modifier(@PathVariable Long id, @Valid @RequestBody TacheRequest request) {
        return ResponseEntity.ok(tacheService.modifier(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacheResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(tacheService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<TacheResponse>> lister(@RequestParam(required = false) Long employeId) {
        if (employeId != null) return ResponseEntity.ok(tacheService.listerParEmploye(employeId));
        return ResponseEntity.ok(tacheService.listerTous());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changerStatus(
            @PathVariable Long id,
            @RequestParam Long idStatusTache,
            @RequestParam(required = false) Long idEmploye) {
        tacheService.changerStatus(id, idStatusTache, idEmploye);
        return ResponseEntity.noContent().build();
    }
}
