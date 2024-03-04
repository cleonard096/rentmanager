package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.DaoException;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;

	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}

	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		return instance;
	}


	public long create(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur().isEmpty() || vehicle.getNbPlaces() < 1) {
			throw new ServiceException("Le constructeur du véhicule ne peut pas être vide et le nombre de places doit être supérieur à 0.",null);
		}
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}
	public  long delete(Vehicle vehicle) throws ServiceException {
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	public int count() throws ServiceException {
		try {
			return VehicleDao.getInstance().count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}
}