package com.kolekta.controller;

import com.kolekta.dto.commande.CommandeRequest;
import com.kolekta.dto.commande.CommandeResponse;
import com.kolekta.service.CommandeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<CommandeResponse> creer(@Valid @RequestBody CommandeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeService.creer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<CommandeResponse>> lister(@RequestParam(required = false) Long clientId) {
        if (clientId != null) return ResponseEntity.ok(commandeService.listerParClient(clientId));
        return ResponseEntity.ok(commandeService.listerTous());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changerStatus(
            @PathVariable Long id,
            @RequestParam Long idStatus,
            @RequestParam(required = false) Long idEmploye) {
        commandeService.changerStatus(id, idStatus, idEmploye);
        return ResponseEntity.noContent().build();
    }
}
