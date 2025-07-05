package com.example.demo.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> origin/sarobidy-admin

@Entity
@Table(name = "status")
public class Status {
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    @OneToMany(mappedBy = "status")
    private List<CommandeStatus> commandeStatusList;

    // Constructeurs, getters et setters
=======

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long id;

    @Column(name = "libelle", nullable = false, length = 100)
    private String libelle;

    // Constructeurs
>>>>>>> origin/sarobidy-admin
    public Status() {
    }

    public Status(String libelle) {
        this.libelle = libelle;
    }
<<<<<<< HEAD
    
=======

>>>>>>> origin/sarobidy-admin
    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
<<<<<<< HEAD

    public List<CommandeStatus> getCommandeStatusList() {
        return commandeStatusList;
    }

    public void setCommandeStatusList(List<CommandeStatus> commandeStatusList) {
        this.commandeStatusList = commandeStatusList;
    }
}
=======
}
>>>>>>> origin/sarobidy-admin
