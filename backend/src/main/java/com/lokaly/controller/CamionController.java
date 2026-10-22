package com.lokaly.controller;

import com.lokaly.dto.camion.AssignationCamionRequest;
import com.lokaly.dto.camion.CamionRequest;
import com.lokaly.dto.camion.CamionResponse;
import com.lokaly.service.CamionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camions")
@RequiredArgsConstructor
public class CamionController {

    private final CamionService camionService;

    @PostMapping
    public ResponseEntity<CamionResponse> creer(@Valid @RequestBody CamionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(camionService.creer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamionResponse> modifier(@PathVariable Long id, @Valid @RequestBody CamionRequest request) {
        return ResponseEntity.ok(camionService.modifier(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamionResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(camionService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<CamionResponse>> listerTous() {
        return ResponseEntity.ok(camionService.listerTous());
    }

    @PostMapping("/assignations")
    public ResponseEntity<Void> assigner(@Valid @RequestBody AssignationCamionRequest request) {
        camionService.assigner(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
