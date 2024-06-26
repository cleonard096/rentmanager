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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository

public class ClientDao {

	private static ClientDao instance = null;
	private ClientDao() {}

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String MODIFY_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

	public long create(Client client) throws DaoException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase","","");
			PreparedStatement statement = connection.prepareStatement(CREATE_CLIENT_QUERY,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, client.getNom());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getEmail());
			statement.setDate(4,Date.valueOf(client.getDateNaissance()));
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
	public long delete(Client client) throws DaoException {
		try {
			Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			PreparedStatement statement = connexion.prepareStatement(DELETE_CLIENT_QUERY);
			statement.setInt(1, client.getClientId());
			statement.execute();

		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return client.getClientId();
	}
	public boolean mailexist(String mail) throws DaoException{
		boolean exists = false;
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM Client WHERE email = ?")) {
			statement.setString(1, mail);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt("count");
					exists = count > 0;
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return exists;
	}

	public Client findById(long id) throws DaoException {
		Client client = null;
		try (Connection connexion = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connexion.prepareStatement(FIND_CLIENT_QUERY)) {
			statement.setLong(1, id);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					String mail = rs.getString("email");
					LocalDate naissance = rs.getDate("naissance").toLocalDate();
					client = new Client((int) id, nom, prenom, mail,naissance);
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			 ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();
				Client cli = new Client(id,nom, prenom, email, naissance);
				clients.add(cli);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return clients;
	}

	public int count() throws DaoException {
		int count = 0;
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS total FROM Client");
			 ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return count;
	}

	public void modify(long clientId, String newNom, String newPrenom, String newEmail, LocalDate newDateNaissance) throws DaoException {

		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "");
			 PreparedStatement statement = connection.prepareStatement(MODIFY_CLIENT_QUERY)) {
			statement.setString(1, newNom);
			statement.setString(2, newPrenom);
			statement.setString(3, newEmail);
			statement.setDate(4, Date.valueOf(newDateNaissance));
			statement.setLong(5, clientId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
	}
	public boolean mailexistForOtherClients(String mail, long clientId) throws DaoException {
		boolean exists = false;
		try (Connection connection = DriverManager.getConnection("jdbc:h2:~/RentManagerDatabase", "", "")) {
			String query = "SELECT COUNT(*) AS count FROM Client WHERE email = ? AND id != ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, mail);
				statement.setLong(2, clientId);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						int count = resultSet.getInt("count");
						exists = count > 0;
					}
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage());
		}
		return exists;
	}
	public List<Client> findByReservations(List<Reservation> reservations) throws DaoException {
		List<Client> clients = new ArrayList<>();
		try {
			for (Reservation reservation : reservations) {
				long clientId = reservation.getClientId();
				Client client = findById(clientId);
				if (client != null) {
					clients.add(client);
				}
			}
		} catch (DaoException e) {
			throw new DaoException(e.getMessage());
		}
		return clients;
	}

}