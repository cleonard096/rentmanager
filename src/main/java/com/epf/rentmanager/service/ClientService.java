package com.epf.rentmanager.service;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

	private final ClientDao clientDao;

	@Autowired
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException {
		if (client.getNom().isEmpty()) {
			throw new ServiceException("Le nom du client ne peut pas être vide.", null);
		}
		if (client.getPrenom().isEmpty()) {
			throw new ServiceException("Le prénom du client ne peut pas être vide.", null);
		}
		if (client.getNom().length()<3 ) {
			throw new ServiceException("Le nom  du client doit contenir au moins 3 caracteres.", null);
		}
		if (client.getPrenom().length()<3) {
			throw new ServiceException("Le prénom du client doit contenir au moins 3 caracteres.", null);
		}
		LocalDate now = LocalDate.now();
		Period period = Period.between(client.getDateNaissance(), now);
		if (period.getYears() < 18) {
			throw new ServiceException("Le client doit avoir au moins 18 ans pour être enregistré.", null);
		}

		try {
			if (clientDao.mailexist(client.getEmail())) {
				throw new ServiceException("L'adresse email est déjà attribué à un client.", null);
			}
			client.setNom(client.getNom().toUpperCase());
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	public List<Client> findByReservations(List<Reservation> reservations) throws ServiceException {
        try {
            return clientDao.findByReservations(reservations);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }


	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public long delete(Client client) throws ServiceException {
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public int count() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	public void modify(long clientId, String newNom, String newPrenom, String newEmail, LocalDate newDateNaissance) throws ServiceException {
		newNom=newNom.toUpperCase();
		if (newNom.isEmpty()) {
			throw new ServiceException("Le nom du client ne peut pas être vide.", null);
		}
		if (newPrenom.isEmpty()) {
			throw new ServiceException("Le prénom du client ne peut pas être vide.", null);
		}
		if (newNom.length()<3 ) {
			throw new ServiceException("Le nom  du client doit contenir au moins 3 caracteres.", null);
		}
		if (newPrenom.length()<3) {
			throw new ServiceException("Le prénom du client doit contenir au moins 3 caracteres.", null);
		}
		LocalDate now = LocalDate.now();
		Period period = Period.between(newDateNaissance, now);
		if (period.getYears() < 18) {
			throw new ServiceException("Le client doit avoir au moins 18 ans pour être enregistré.", null);
		}
        try {
            if (clientDao.mailexistForOtherClients(newEmail,clientId)) {
                throw new ServiceException("L'adresse email est déjà attribué à un client.", null);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        try {
			clientDao.modify( clientId, newNom, newPrenom, newEmail, newDateNaissance);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

}
