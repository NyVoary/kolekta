package com.example.demo.controller.admin.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StatistiqueDTO;
import com.example.demo.service.AssignationTacheService;

@RestController
@RequestMapping("/admin/tache")
public class AssignationTacheController {

    private final AssignationTacheService service;

    public AssignationTacheController(AssignationTacheService service) {
        this.service = service;
    }
    
    @GetMapping("/stat")
    public StatistiqueDTO getStat() {
        return service.getStat();
    }
}
