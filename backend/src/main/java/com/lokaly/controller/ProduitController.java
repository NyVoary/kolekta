package com.lokaly.controller;

import com.lokaly.dto.produit.ProduitRequest;
import com.lokaly.dto.produit.ProduitResponse;
import com.lokaly.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @PostMapping
    public ResponseEntity<ProduitResponse> creer(@Valid @RequestBody ProduitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.creer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitResponse> modifier(@PathVariable Long id, @Valid @RequestBody ProduitRequest request) {
        return ResponseEntity.ok(produitService.modifier(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProduitResponse>> lister(
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) String search) {
        if (categorieId != null) return ResponseEntity.ok(produitService.listerParCategorie(categorieId));
        if (search != null && !search.isBlank()) return ResponseEntity.ok(produitService.rechercher(search));
        return ResponseEntity.ok(produitService.listerTous());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        produitService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
