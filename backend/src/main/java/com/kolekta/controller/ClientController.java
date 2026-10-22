package com.kolekta.controller;

import com.kolekta.dto.client.ClientRequest;
import com.kolekta.dto.client.ClientResponse;
import com.kolekta.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/inscription")
    public ResponseEntity<ClientResponse> inscrire(@Valid @RequestBody ClientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.inscrire(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> modifier(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok(clientService.modifier(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> listerActifs() {
        return ResponseEntity.ok(clientService.listerActifs());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactiver(@PathVariable Long id) {
        clientService.desactiver(id);
        return ResponseEntity.noContent().build();
    }
}
