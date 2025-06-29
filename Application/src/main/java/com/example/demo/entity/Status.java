package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Integer idStatus;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @OneToMany(mappedBy = "status")
    private List<CommandeStatus> commandeStatusList;

    // Constructeurs, getters et setters
    public Status() {
    }

    public Status(String libelle) {
        this.libelle = libelle;
    }

    // Getters et Setters
    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<CommandeStatus> getCommandeStatusList() {
        return commandeStatusList;
    }

    public void setCommandeStatusList(List<CommandeStatus> commandeStatusList) {
        this.commandeStatusList = commandeStatusList;
    }
}