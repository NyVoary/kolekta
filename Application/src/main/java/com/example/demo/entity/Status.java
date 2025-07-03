package com.example.demo.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> g1-details-commande

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private Long id;
    
    private String libelle;
    
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
=======
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
>>>>>>> g1-details-commande
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
<<<<<<< HEAD
=======

    public List<CommandeStatus> getCommandeStatusList() {
        return commandeStatusList;
    }

    public void setCommandeStatusList(List<CommandeStatus> commandeStatusList) {
        this.commandeStatusList = commandeStatusList;
    }
>>>>>>> g1-details-commande
}