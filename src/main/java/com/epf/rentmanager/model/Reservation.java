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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.epf.rentmanager.persistence.ConnectionManager;
import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;
    private int clientId;
    private int vehicleId;
    private LocalDate debut;
    private LocalDate fin;

    // Constructeur vide
    public Reservation() {
        // Vous pouvez initialiser les attributs avec des valeurs par défaut si nécessaire
        // Par exemple :
        // this.reservationId = 0;
        // this.clientId = 0;
        // this.vehicleId = 0;
        // this.debut = LocalDateTime.now(); // Date de début par défaut à maintenant
        // this.fin = LocalDateTime.now();   // Date de fin par défaut à maintenant
    }

    // Constructeur avec paramètres
    public Reservation(int reservationId, int clientId, int vehicleId, LocalDate debut, LocalDate fin) {
        this.reservationId = reservationId;
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation( int clientId, int vehicleId, LocalDate debut, LocalDate fin) {
        this.clientId = clientId;
        this.vehicleId = vehicleId;
        this.debut = debut;
        this.fin = fin;
    }

    // Getters et Setters (omis pour la brièveté)

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", clientId=" + clientId +
                ", vehicleId=" + vehicleId +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
}
