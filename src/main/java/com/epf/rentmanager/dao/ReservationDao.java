package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?)";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase","","");
			PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATION_QUERY,Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, reservation.getClientId());
			statement.setInt(2, reservation.getVehicleId());
			statement.setDate(3,Date.valueOf(reservation.getDebut()));
			statement.setDate(4, Date.valueOf(reservation.getFin()));
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
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			statement.setInt(1,reservation.getReservationId());
			statement.execute();
			connection.close();
			statement.close();
		    }
		catch (SQLException e) {
			throw new DaoException(e.getMessage());
        }
        return reservation.getReservationId();
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			statement.setLong(1, clientId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					int vehicle_id = resultSet.getInt("vehicle_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();
					Reservation reser = new Reservation(id, (int)clientId, vehicle_id, debut, fin);
					reservations.add(reser);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {
			statement.setLong(1, vehicleId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					int clientId = resultSet.getInt("client_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();
					Reservation reser = new Reservation(id, clientId, (int) vehicleId, debut, fin);
					reservations.add(reser);
				}
			}
		}
		catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}return reservations;
	}
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int clientId = resultSet.getInt("client_id");
				int vehicleId = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				Reservation reser = new Reservation(id, clientId, vehicleId, debut, fin);
				reservations.add(reser);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}
	public Reservation findById(long id) throws DaoException {
		Reservation reservation = null;
		try (Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connexion.prepareStatement(FIND_RESERVATION_QUERY)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int clientId = resultSet.getInt("client_id");
					int vehicleId = resultSet.getInt("vehicle_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();
					reservation = new Reservation((int)id, clientId, vehicleId, debut, fin);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservation;
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS total FROM Reservation");
			 ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException("Error while counting reservations: " + e.getMessage());
		}
		return 0;
	}

}