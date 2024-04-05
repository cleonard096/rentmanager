package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static java.time.Period.ZERO;

@Repository
public class ReservationDao {

	private ConnectionManager connectionManager;

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?)";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATION_QUERY = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_AND_DATE_QUERY = "SELECT id,client_id, debut, fin FROM Reservation WHERE vehicle_id = ? AND (debut BETWEEN ? AND ? OR fin BETWEEN ? AND ?)";


	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, reservation.getClientId());
			statement.setInt(2, reservation.getVehicleId());
			statement.setDate(3, Date.valueOf(reservation.getDebut()));
			statement.setDate(4, Date.valueOf(reservation.getFin()));
			statement.execute();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return 0;
	}

	public long delete(Reservation reservation) throws DaoException {
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
			statement.setInt(1, reservation.getReservationId());
			statement.execute();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservation.getReservationId();
	}

	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {
			statement.setLong(1, clientId);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					int vehicle_id = resultSet.getInt("vehicle_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();
					Reservation reser = new Reservation(id, (int) clientId, vehicle_id, debut, fin);
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
		try (Connection connection = connectionManager.getConnection();
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
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
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
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATION_QUERY)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int clientId = resultSet.getInt("client_id");
					int vehicleId = resultSet.getInt("vehicle_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();
					reservation = new Reservation((int) id, clientId, vehicleId, debut, fin);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservation;
	}

	public int count() throws DaoException {
		try (Connection connection = connectionManager.getConnection();
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
	public List<Reservation> findResaByVehicleIdAndDate(long vehicleId, LocalDate debut, LocalDate fin) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_AND_DATE_QUERY)) {
			statement.setLong(1, vehicleId);
			statement.setDate(2, Date.valueOf(debut));
			statement.setDate(3, Date.valueOf(fin));
			statement.setDate(4, Date.valueOf(debut));
			statement.setDate(5, Date.valueOf(fin));
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					int clientId = resultSet.getInt("client_id");
					LocalDate resaDebut = resultSet.getDate("debut").toLocalDate();
					LocalDate resaFin = resultSet.getDate("fin").toLocalDate();
					Reservation reser = new Reservation(id, clientId, (int) vehicleId, resaDebut, resaFin);
					reservations.add(reser);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return reservations;
	}

	public Period findPeriodBookedBeforeDate(long vehicleId, LocalDate date) throws DaoException {
		Period totalPeriod = Period.ZERO;
		LocalDate previousReservationSart = date;
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT debut, fin FROM Reservation WHERE vehicle_id = ? AND fin <= ? ORDER BY debut DESC")) {
			statement.setLong(1, vehicleId);
			statement.setDate(2, Date.valueOf(date));

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					LocalDate reservationStart = resultSet.getDate("debut").toLocalDate();
					LocalDate reservationEnd = resultSet.getDate("fin").toLocalDate();
					if (!previousReservationSart.minusDays(1).equals(reservationEnd)) {
						break;
					}
					else{
					Period period = Period.between(reservationStart, reservationEnd);
					totalPeriod = totalPeriod.plus(period).plusDays(1);
					previousReservationSart = reservationStart;
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return totalPeriod;
	}
	public Period findPeriodBookedAfterDate(long vehicleId, LocalDate date) throws DaoException {
		Period totalPeriod = Period.ZERO;
		LocalDate previousReservationEnd = date;

		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT debut, fin FROM Reservation WHERE vehicle_id = ? AND debut >= ? ORDER BY debut ASC")) {
			statement.setLong(1, vehicleId);
			statement.setDate(2, Date.valueOf(date));

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					LocalDate reservationStart = resultSet.getDate("debut").toLocalDate();
					LocalDate reservationEnd = resultSet.getDate("fin").toLocalDate();

					if (!previousReservationEnd.plusDays(1).equals(reservationStart)) {
						break;
					}

					Period period = Period.between(reservationStart, reservationEnd);
					totalPeriod = totalPeriod.plus(period);

					previousReservationEnd = reservationEnd;
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}

		return totalPeriod;
	}
	public Period findPeriodBookedBeforeDateByClient(long clientId, long vehicleId, LocalDate date) throws DaoException {
		Period totalPeriod = Period.ZERO;
		LocalDate previousReservationSart = date;
		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 "SELECT debut, fin FROM Reservation WHERE client_id = ? AND vehicle_id = ? AND fin <= ? ORDER BY debut DESC")) {
			statement.setLong(1, clientId);
			statement.setLong(2, vehicleId);
			statement.setDate(3, Date.valueOf(date));

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					LocalDate reservationStart = resultSet.getDate("debut").toLocalDate();
					LocalDate reservationEnd = resultSet.getDate("fin").toLocalDate();
					if (!previousReservationSart.minusDays(1).equals(reservationEnd)) {
						break;
					} else {
						Period period = Period.between(reservationStart, reservationEnd);
						totalPeriod = totalPeriod.plus(period).plusDays(1);
						previousReservationSart = reservationStart;
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return totalPeriod;
	}
	public Period findPeriodBookedAfterDateByClient(long clientId, long vehicleId, LocalDate date) throws DaoException {
		Period totalPeriod = Period.ZERO;
		LocalDate previousReservationEnd = date;

		try (Connection connection = connectionManager.getConnection();
			 PreparedStatement statement = connection.prepareStatement(
					 "SELECT debut, fin FROM Reservation WHERE client_id = ? AND vehicle_id = ? AND debut >= ? ORDER BY debut ASC")) {
			statement.setLong(1, clientId);
			statement.setLong(2, vehicleId);
			statement.setDate(3, Date.valueOf(date));

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					LocalDate reservationStart = resultSet.getDate("debut").toLocalDate();
					LocalDate reservationEnd = resultSet.getDate("fin").toLocalDate();

					if (!previousReservationEnd.plusDays(1).equals(reservationStart)) {
						break;
					}

					Period period = Period.between(reservationStart, reservationEnd);
					totalPeriod = totalPeriod.plus(period);

					previousReservationEnd = reservationEnd;
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}

		return totalPeriod;
	}
	public void modify(long reservationId, LocalDate newDebut, LocalDate newFin) throws DaoException {
		try (Connection connection = connectionManager.getConnection()) {
			if (newDebut != null) {
				try (PreparedStatement statement = connection.prepareStatement("UPDATE Reservation SET debut=? WHERE id=?")) {
					statement.setDate(1, Date.valueOf(newDebut));
					statement.setLong(2, reservationId);
					statement.executeUpdate();
				}
			}
			if (newFin != null) {
				try (PreparedStatement statement = connection.prepareStatement("UPDATE Reservation SET fin=? WHERE id=?")) {
					statement.setDate(1, Date.valueOf(newFin));
					statement.setLong(2, reservationId);
					statement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	public static List<Reservation> removeReservationById(List<Reservation> reservations, long idToRemove) {
		List<Reservation> updatedReservations = new ArrayList<>();
		for (Reservation reservation : reservations) {
			if (reservation.getReservationId() != idToRemove) {
				updatedReservations.add(reservation);
			}
		}
		return updatedReservations;
	}





}