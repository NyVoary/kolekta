package com.example.demo.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> Robel

@Entity
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private Long id;
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
=======
    @Column(name = "id_status")
    private Long id;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    // Getters et setters
>>>>>>> Robel
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
>>>>>>> Robel
