package com.example.demo.dto;

import java.time.LocalDate;

public class DateRangeDTO {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    
    // Getters & Setters
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public LocalDate getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}