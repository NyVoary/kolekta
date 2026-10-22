package com.lokaly.controller;

import com.lokaly.dto.panier.AjoutPanierRequest;
import com.lokaly.dto.panier.PanierResponse;
import com.lokaly.service.PanierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients/{idClient}/panier")
@RequiredArgsConstructor
public class PanierController {

    private final PanierService panierService;

    @GetMapping
    public ResponseEntity<PanierResponse> obtenir(@PathVariable Long idClient) {
        return ResponseEntity.ok(panierService.obtenirPanierActif(idClient));
    }

    @PostMapping("/produits")
    public ResponseEntity<PanierResponse> ajouter(@PathVariable Long idClient, @Valid @RequestBody AjoutPanierRequest request) {
        return ResponseEntity.ok(panierService.ajouterProduit(idClient, request));
    }

    @PatchMapping("/produits/{idProduit}")
    public ResponseEntity<PanierResponse> modifierQuantite(
            @PathVariable Long idClient,
            @PathVariable Long idProduit,
            @RequestParam Integer quantite) {
        return ResponseEntity.ok(panierService.modifierQuantite(idClient, idProduit, quantite));
    }

    @DeleteMapping("/produits/{idProduit}")
    public ResponseEntity<Void> supprimer(@PathVariable Long idClient, @PathVariable Long idProduit) {
        panierService.supprimerProduit(idClient, idProduit);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> vider(@PathVariable Long idClient) {
        panierService.viderPanier(idClient);
        return ResponseEntity.noContent().build();
    }
}
