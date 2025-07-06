package com.example.demo.dto;

public class AssignationCamionDTO {
    private int idCamion;
    private String depart;
    private String arrivee; // facultatif
    private String type;    // "Collecte", "Livraison", "Maintenance"

    public int getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrivee() {
        return arrivee;
    }

    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
