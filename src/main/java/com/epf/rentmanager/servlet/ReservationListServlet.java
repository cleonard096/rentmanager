package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ReservationService reservationService;

    public ReservationListServlet() {
        super();
        this.reservationService = ReservationService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Reservation> reservations = reservationService.findAll();

            for (Reservation reservation : reservations) {
                Client client = ClientService.getInstance().findById(reservation.getClientId());
                String clientFullName = client.getNom() + " " + client.getPrenom();
                reservation.setClientFullName(clientFullName);

                Vehicle vehicle = VehicleService.getInstance().findById(reservation.getVehicleId());
                String vehicleDescr = vehicle.getConstructeur() + " " + vehicle.getModele();
                reservation.setVehicleDescr(vehicleDescr);
            }

            request.setAttribute("reservations", reservations);

            request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("An error occurred while retrieving the reservation list", e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long reservationId = Long.parseLong(request.getParameter("reservationId"));
            Reservation reservation = new Reservation();
            reservation = ReservationService.getInstance().findById(reservationId);
            reservationService.delete(reservation);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException | ServiceException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}