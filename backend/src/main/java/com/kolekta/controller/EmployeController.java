package com.kolekta.controller;

import com.kolekta.dto.employe.AccesRequest;
import com.kolekta.dto.employe.EmployeRequest;
import com.kolekta.dto.employe.EmployeResponse;
import com.kolekta.service.EmployeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeResponse> creer(@Valid @RequestBody EmployeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.creer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeResponse> modifier(@PathVariable Long id, @Valid @RequestBody EmployeRequest request) {
        return ResponseEntity.ok(employeService.modifier(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeResponse> trouverParId(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.trouverParId(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeResponse>> lister(@RequestParam(defaultValue = "true") boolean actifs) {
        return ResponseEntity.ok(actifs ? employeService.listerActifs() : employeService.listerTous());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactiver(@PathVariable Long id) {
        employeService.desactiver(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/acces")
    public ResponseEntity<Void> creerAcces(@Valid @RequestBody AccesRequest request) {
        employeService.creerAcces(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
