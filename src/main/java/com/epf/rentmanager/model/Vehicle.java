package com.epf.rentmanager.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.persistence.ConnectionManager;

public class Vehicle {
    private int vehicleId;
    private String constructeur;
    private String modele;
    private int nbPlaces;
    public Vehicle() {

    }
    // Constructeur
    public Vehicle(int vehicleId, String constructeur, String modele, int nbPlaces) {
        this.vehicleId = vehicleId;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nbPlaces = nbPlaces;
    }
    public Vehicle( String constructeur, String modele, int nbPlaces) {
        this.vehicleId = vehicleId;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nbPlaces = nbPlaces;
    }
    public Vehicle( String constructeur, int nbPlaces) {
        this.constructeur = constructeur;
        this.nbPlaces = nbPlaces;
    }
    public Vehicle(int vehicleId, String constructeur, int nbPlaces) {
        this.vehicleId = vehicleId;
        this.constructeur = constructeur;
        this.nbPlaces = nbPlaces;
    }
    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + vehicleId +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nbPlaces=" + nbPlaces +
                '}';
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }
}