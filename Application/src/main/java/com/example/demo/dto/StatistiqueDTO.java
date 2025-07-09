package com.example.demo.dto;

public class StatistiqueDTO {

    private int en_attente;
    private int en_cours;
    private int termine;
    private int total;

    public StatistiqueDTO(int en_attente, int en_cours, int termine, int total) {
        this.en_attente = en_attente;
        this.en_cours = en_cours;
        this.termine = termine;
        this.total = total;
    }

    public int getEn_attente() {
        return en_attente;
    }

    public void setEn_attente(int en_attente) {
        this.en_attente = en_attente;
    }

    public int getEn_cours() {
        return en_cours;
    }

    public void setEn_cours(int en_cours) {
        this.en_cours = en_cours;
    }

    public int getTermine() {
        return termine;
    }

    public void setTermine(int termine) {
        this.termine = termine;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
