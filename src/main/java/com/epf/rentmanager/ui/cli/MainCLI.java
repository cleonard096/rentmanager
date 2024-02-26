package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class MainCLI {

    private static final ClientService clientService = ClientService.getInstance();
    private static final VehicleService vehicleService = VehicleService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();

    public static void main(String[] args) {
        displayMainMenu();
    }

    private static void displayMainMenu() {
        boolean running = true;
        while (running) {
            IOUtils.print("\n### Menu principal ###");
            IOUtils.print("1. Créer un client");
            IOUtils.print("2. Lister tous les clients");
            IOUtils.print("3. Créer un véhicule");
            IOUtils.print("4. Lister tous les véhicules");
            IOUtils.print("5. Créer une réservation");
            IOUtils.print("6. Lister toutes les réservations");
            IOUtils.print("7. Lister les réservations associées à un client");
            IOUtils.print("8. Lister les réservations associées à un véhicule");
            IOUtils.print("9. Supprimer un client (bonus)");
            IOUtils.print("10. Supprimer un véhicule (bonus)");
            IOUtils.print("11. Supprimer une réservation");
            IOUtils.print("12. Quitter");

            int choice = IOUtils.readInt("Choisissez une option : ", true);

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listClients();
                    break;
                case 3:
                    createVehicle();
                    break;
                case 4:
                    listVehicles();
                    break;
                case 5:
                    createReservation();
                    break;
                case 6:
                    listReservations();
                    break;
                case 7:
                    listReservationsByClient();
                    break;
                case 8:
                    listReservationsByVehicle();
                    break;
                case 9:
                    deleteClient();
                    break;
                case 10:
                    deleteVehicle();
                    break;
                case 11:
                    deleteReservation();
                    break;
                case 12:
                    running = false;
                    IOUtils.print("Au revoir !");
                    break;
                default:
                    IOUtils.print("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void createClient() {
        IOUtils.print("\n### Création d'un client ###");
        String nom = IOUtils.readString("Nom : ",true);
        String prenom = IOUtils.readString("Prénom : ",true);
        String email = IOUtils.readString("Email : ",true);
        LocalDate naissance = IOUtils.readDate("Date de naissance (format dd/MM/yyyy) : ",true);

        try {
            long clientId = clientService.create(new Client(nom, prenom, email, naissance));
            IOUtils.print("Client créé avec succès ! (ID : " + clientId + ")");
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    private static void listClients() {
        IOUtils.print("\n### Liste des clients ###");
        try {
            List<Client> clients = clientService.findAll();
            if (!clients.isEmpty()) {
                for (Client client : clients) {
                    IOUtils.print(client.toString());
                }
            } else {
                IOUtils.print("Aucun client trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des clients : " + e.getMessage());
        }
    }

    private static void createVehicle() {
        IOUtils.print("\n### Création d'un véhicule ###");

        String constructeur = IOUtils.readString("Constructeur : ",true);
        int nbPlaces = IOUtils.readInt("Nombre de places : ",true);
        try {
            long vehicleId = vehicleService.create(new Vehicle(constructeur,nbPlaces));
            IOUtils.print("Véhicule créé avec succès ! (ID : " + vehicleId + ")");
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la création du véhicule : " + e.getMessage());
        }
    }

    private static void listVehicles() {
        IOUtils.print("\n### Liste des véhicules ###");
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
            if (!vehicles.isEmpty()) {
                for (Vehicle vehicle : vehicles) {
                    IOUtils.print(vehicle.toString());
                }
            } else {
                IOUtils.print("Aucun véhicule trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des véhicules : " + e.getMessage());
        }
    }

    private static void createReservation() {
        IOUtils.print("\n### Création d'une réservation ###");
        int clientId = IOUtils.readInt("ID du client : ", true);
        int vehicleId = IOUtils.readInt("ID du véhicule : ", true);
        LocalDate debut = IOUtils.readDate("Date de début (format dd/MM/yyyy) : ", true);
        LocalDate fin = IOUtils.readDate("Date de fin (format dd/MM/yyyy) : ", true);

        try {
            long reservationId = reservationService.create(new Reservation(0, clientId, vehicleId, debut, fin));
            IOUtils.print("Réservation créée avec succès ! (ID : " + reservationId + ")");
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    private static void listReservations() {
        IOUtils.print("\n### Liste des réservations ###");
        try {
            List<Reservation> reservations = reservationService.findAll();
            if (!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    IOUtils.print(reservation.toString());
                }
            } else {
                IOUtils.print("Aucune réservation trouvée.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }

    private static void listReservationsByClient() {
        int clientId = IOUtils.readInt("ID du client : ", true);
        try {
            List<Reservation> reservations = reservationService.findResaByClientId(clientId);
            if (!reservations.isEmpty()) {
                IOUtils.print("Réservations associées au client " + clientId + " :");
                for (Reservation reservation : reservations) {
                    IOUtils.print(reservation.toString());
                }
            } else {
                IOUtils.print("Aucune réservation associée à ce client.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }

    private static void listReservationsByVehicle() {
        int vehicleId = IOUtils.readInt("ID du véhicule : ", true);
        try {
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);
            if (!reservations.isEmpty()) {
                IOUtils.print("Réservations associées au véhicule " + vehicleId + " :");
                for (Reservation reservation : reservations) {
                    IOUtils.print(reservation.toString());
                }
            } else {
                IOUtils.print("Aucune réservation associée à ce véhicule.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }

    private static void deleteClient() {
        IOUtils.print("\n### Suppression d'un client ###");
        int clientId = IOUtils.readInt("ID du client à supprimer : ",true);

        try {
            Client client = clientService.findById((long)clientId);
            if (client != null) {
                clientService.delete(client);
                IOUtils.print("Client supprimé avec succès !");
            } else {
                IOUtils.print("Aucun client trouvé avec l'ID spécifié.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la suppression du client : " + e.getMessage());
        }
    }

    private static void deleteVehicle() {
        IOUtils.print("\n### Suppression d'un véhicule ###");
        int vehicleId = IOUtils.readInt("ID du véhicule à supprimer : ",true);
        try {
            Vehicle vehicle = vehicleService.findById(vehicleId);
            if (vehicle != null) {
                vehicleService.delete(vehicle);
                IOUtils.print("Véhicule supprimé avec succès !");
            } else {
                IOUtils.print("Aucun véhicule trouvé avec l'ID spécifié.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la suppression du véhicule : " + e.getMessage());
        }
    }

    private static void deleteReservation() {
        int reservationId = IOUtils.readInt("ID de la réservation à supprimer : ", true);
        try {
            Reservation reservation = reservationService.findById(reservationId);
            if (reservation != null) {
                reservationService.delete(reservation);
                IOUtils.print("Réservation supprimée avec succès !");
            } else {
                IOUtils.print("Aucune réservation trouvée avec l'ID spécifié.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }
    }
}