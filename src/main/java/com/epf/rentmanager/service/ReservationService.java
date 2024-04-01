package com.epf.rentmanager.service;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    @Autowired
    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        Period period = Period.between(reservation.getDebut(), reservation.getFin()).plusDays(1);
        if (period.getDays()>7){
            throw new ServiceException("La durée maximum de location est de 7 jours", null);
        }
        try {
            Period periodBooked = reservationDao.findPeriodBookedBeforeDate(reservation.getVehicleId(), reservation.getDebut());
            System.out.println("Service");
            System.out.println(periodBooked.plus(period).getDays());
            if (periodBooked.plus(period).getDays() > 30) {
                throw new ServiceException("La voiture ne peut pas être réservée 30 jours de suite sans pause.", null);
            }

        try {
            List<Reservation> existingReservations = reservationDao.findResaByVehicleIdAndDate(
                    reservation.getVehicleId(), reservation.getDebut(), reservation.getFin());
            if (!existingReservations.isEmpty()) {
                throw new ServiceException("La voiture est déjà réservée pour les dates spécifiées.", null);
            }
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    } catch (DaoException e) {
            throw new RuntimeException(e);
        }}


        public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
        if (reservation.getVehicleId() == 0) {
            throw new ServiceException("La reservation n'est lié a aucun vehicule", null);
        }
        if (reservation.getClientId() == 0) {
            throw new ServiceException("La reservation n'est lié a aucun client", null);
        }
        try {
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public int count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Error while counting reservations: " + e.getMessage(), e);
        }
    }
}