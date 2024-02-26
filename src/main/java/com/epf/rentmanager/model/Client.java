package com.epf.rentmanager.model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.epf.rentmanager.persistence.ConnectionManager;

public class Client {
    private int clientId;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;

    // Constructeur vide
    public Client() {
        // this.id = 0;
        // this.nom = "";
        // this.prenom = "";
        // this.email = "";
        // this.dateNaissance = LocalDate.now(); // Date de naissance par défaut à aujourd'hui
    }

    // Constructeur avec paramètres
    public Client(int clientId, String nom, String prenom, String email, LocalDate dateNaissance) {
        this.clientId = clientId;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    public Client(String nom, String prenom, String email, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    // Getters et Setters (omis pour la brièveté)

    @Override
    public String toString() {
        return "Client{" +
                "id=" + clientId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}