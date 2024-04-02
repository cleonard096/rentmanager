package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

	private final VehicleDao vehicleDao;

	@Autowired
	public VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur().isEmpty() || vehicle.getModele().isEmpty()) {
			throw new ServiceException("Le modèle et le constructeur du véhicule ne peut pas être vide", null);
		}
		if ((vehicle.getNbPlaces()>=10 || vehicle.getNbPlaces()<=1)) {
			throw new ServiceException("Le nombre de place doit être compris entre 2 et 9", null);
		}
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
	public void modify(long vehicleId, String newConstructeur, String newModele, int newNbPlaces) throws ServiceException {
		if ((newNbPlaces>=10 || newNbPlaces<=1)) {
			throw new ServiceException("Le nombre de place doit être compris entre 2 et 9", null);
		}
		try {
			vehicleDao.modify(vehicleId, newConstructeur, newModele, newNbPlaces);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}