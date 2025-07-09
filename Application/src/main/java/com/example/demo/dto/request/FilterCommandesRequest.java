package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class FilterCommandesRequest {
    private String statut;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;
    
    private String sortBy = "dateCommande";
    private String sortDirection = "DESC";
}