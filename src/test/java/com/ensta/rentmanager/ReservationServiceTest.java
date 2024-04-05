package com.ensta.rentmanager;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.DaoException;
import org.junit.Before;
import org.junit.Test;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;

public class ReservationServiceTest {
    private ReservationDao reservationDaoMock;
    private ReservationService reservationService;

    @Before
    public void setUp() {
        reservationDaoMock = mock(ReservationDao.class);
        reservationService = new ReservationService(reservationDaoMock);
    }

    @Test
    public void testCreateReservation() throws ServiceException, DaoException {
        Reservation reservation = new Reservation();
        reservation.setDebut(LocalDate.of(2024, 4, 1));
        reservation.setFin(LocalDate.of(2024, 4, 7));
        reservation.setClientId(1);
        reservation.setVehicleId(1);

        try {
            when(reservationDaoMock.findResaByVehicleIdAndDate(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                    .thenReturn(new ArrayList<>());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        try {
            when(reservationDaoMock.create(reservation)).thenReturn(1L);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        long reservationId = reservationService.create(reservation);

        assertEquals(1L, reservationId);
        verify(reservationDaoMock).create(reservation);
    }

    @Test(expected = ServiceException.class)
    public void testCreateReservationWithInvalidDates() throws ServiceException {
        Reservation reservation = new Reservation();
        reservation.setDebut(LocalDate.of(2024, 4, 7));
        reservation.setFin(LocalDate.of(2024, 4, 1));
        reservation.setClientId(1);
        reservation.setVehicleId(1);

        reservationService.create(reservation);
    }

    @Test(expected = ServiceException.class)
    public void testModifyReservationWithInvalidDates() throws ServiceException {
        long reservationId = 1L;
        LocalDate newDebut = LocalDate.of(2024, 4, 7);
        LocalDate newFin = LocalDate.of(2024, 4, 1);

        reservationService.modify(reservationId, newDebut, newFin);
    }
    @Test
    public void testDeleteReservation() throws ServiceException, DaoException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1);
        reservation.setDebut(LocalDate.of(2024, 4, 1));
        reservation.setFin(LocalDate.of(2024, 4, 7));
        reservation.setClientId(1);
        reservation.setVehicleId(1);

        when(reservationDaoMock.delete(reservation)).thenReturn((long) reservation.getReservationId());

        long deletedReservationId = reservationService.delete(reservation);

        assertEquals(reservation.getReservationId(), deletedReservationId);
        verify(reservationDaoMock).delete(reservation);
    }

    @Test
    public void testFindByIdReservation() throws ServiceException, DaoException {
        long reservationId = 1L;
        Reservation expectedReservation = new Reservation();
        expectedReservation.setReservationId((int) reservationId);
        expectedReservation.setDebut(LocalDate.of(2024, 4, 1));
        expectedReservation.setFin(LocalDate.of(2024, 4, 7));
        expectedReservation.setClientId(1);
        expectedReservation.setVehicleId(1);

        when(reservationDaoMock.findById(reservationId)).thenReturn(expectedReservation);

        Reservation foundReservation = reservationService.findById(reservationId);

        assertNotNull(foundReservation);
        assertEquals(expectedReservation, foundReservation);
    }

    @Test(expected = ServiceException.class)
    public void testCreateReservationWhenVehicleAlreadyReserved() throws ServiceException, DaoException {
        Reservation reservation = new Reservation();
        reservation.setDebut(LocalDate.of(2024, 4, 1));
        reservation.setFin(LocalDate.of(2024, 4, 7));
        reservation.setClientId(1);
        reservation.setVehicleId(1);

        List<Reservation> existingReservations = new ArrayList<>();
        existingReservations.add(new Reservation());
        when(reservationDaoMock.findResaByVehicleIdAndDate(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(existingReservations);
        reservationService.create(reservation);
    }

    @Test(expected = ServiceException.class)
    public void testCreateReservationExceedMaxDaysPerClient() throws ServiceException, DaoException {
        Reservation reservation = new Reservation();
        reservation.setDebut(LocalDate.of(2024, 4, 1));
        reservation.setFin(LocalDate.of(2024, 4, 10)); // Duration exceeds 7 days
        reservation.setClientId(1);
        reservation.setVehicleId(1);

        // Mocking method calls
        when(reservationDaoMock.findPeriodBookedBeforeDateByClient(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(Period.ofDays(0)); // Simulating no prior bookings
        when(reservationDaoMock.findPeriodBookedAfterDateByClient(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(Period.ofDays(0)); // Simulating no future bookings
        when(reservationDaoMock.create(reservation)).thenReturn(1L);

        reservationService.create(reservation);
    }

}