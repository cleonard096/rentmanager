package com.epf.rentmanager.dao;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;
@Repository
public class VehicleDao {
	private static VehicleDao instance = null;
	private VehicleDao() {
	}
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur,modele ,nb_places) VALUES(?, ?,?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur,modele ,nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur,modele, nb_places FROM Vehicle;";

	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase","","");
			PreparedStatement statement = connection.prepareStatement(CREATE_VEHICLE_QUERY,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, vehicle.getConstructeur());
			statement.setString(2, vehicle.getModele());
			statement.setInt(3, vehicle.getNbPlaces());
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
			connection.close();
			statement.close();
			resultSet.close();
		}
		 catch (SQLException e) {
			 throw new DaoException(e.getMessage());
		} return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement statement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			statement.setInt(1,vehicle.getVehicleId());
			statement.execute();
			connection.close();
			statement.close();
		}
		catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return vehicle.getVehicleId();
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicule = null;
		try (Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connexion.prepareStatement(FIND_VEHICLE_QUERY)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String constructeur = resultSet.getString("constructeur");
					String modele = resultSet.getString("modele");
					int nb_places = resultSet.getInt("nb_places");
					vehicule = new Vehicle((int)id, constructeur,modele, nb_places);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return vehicule;
	}
	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nb_places = resultSet.getInt("nb_places");
				Vehicle veh = new Vehicle(id,constructeur,modele, nb_places);
				vehicles.add(veh);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return vehicles;
	}

	public int count() throws DaoException {
		int count = 0;
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS total FROM Vehicle");
			 ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				count = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return count;
	}
	public void modify(long vehicleId, String newConstructeur, String newModele, int newNbPlaces) throws DaoException {
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "")) {
			if (newConstructeur != null) {
				try (PreparedStatement statement = connection.prepareStatement("UPDATE Vehicle SET constructeur=? WHERE id=?")) {
					statement.setString(1, newConstructeur);
					statement.setLong(2, vehicleId);
					statement.executeUpdate();
				}
			}
			if (newModele != null) {
				try (PreparedStatement statement = connection.prepareStatement("UPDATE Vehicle SET modele=? WHERE id=?")) {
					statement.setString(1, newModele);
					statement.setLong(2, vehicleId);
					statement.executeUpdate();
				}
			}
			if (newNbPlaces != -1) {
				try (PreparedStatement statement = connection.prepareStatement("UPDATE Vehicle SET nb_places=? WHERE id=?")) {
					statement.setInt(1, newNbPlaces);
					statement.setLong(2, vehicleId);
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}

	public List<Vehicle> findByReservations(List<Reservation> reservations) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try {
			for (Reservation reservation : reservations) {
				long vehicleId = reservation.getVehicleId();
				Vehicle vehicle = findById(vehicleId);
				if (vehicle != null) {
					vehicles.add(vehicle);
				}
			}
		} catch (DaoException e) {
			throw new DaoException(e.getMessage());
		}
		return vehicles;
	}


}