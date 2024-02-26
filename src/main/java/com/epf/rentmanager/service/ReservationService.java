package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.util.List;

public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }
    public long create(Reservation reservation) throws ServiceException {
        if (reservation.getDebut().isAfter(reservation.getFin())) {
            throw new ServiceException("La fin de la reservation doit etre après le debut.", null);
        }
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public  long delete(Reservation reservation) throws ServiceException {
        if (reservation.getVehicleId()==0 ) {
            throw new ServiceException("La reservation n'est lié a aucun vehicule", null);
        }
        if (reservation.getClientId()==0 ) {
            throw new ServiceException("La reservation n'est lié a aucun vehicule", null);
        }
        try {
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }
}
