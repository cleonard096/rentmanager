package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Client> clients = clientService.findAll();
            List<Vehicle> vehicles = vehicleService.findAll();
            request.setAttribute("clients", clients);
            request.setAttribute("vehicles", vehicles);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error-page.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int vehicleId = Integer.parseInt(request.getParameter("car"));
        int clientId = Integer.parseInt(request.getParameter("client"));
        LocalDate debut = LocalDate.parse(request.getParameter("begin"));
        LocalDate fin = LocalDate.parse(request.getParameter("end"));
        Reservation reservation= new Reservation();
        reservation.setClientId(clientId);
        reservation.setVehicleId(vehicleId);
        reservation.setDebut(debut);
        reservation.setFin(fin);

        try {
            reservationService.create(reservation);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException e) {
            e.printStackTrace();
            if (e.getMessage().startsWith("La fin")) {
                request.setAttribute("dateError", e.getMessage());
            } else {
                request.setAttribute("error", e.getMessage());
            }
            // Rechargez les listes de clients et de véhicules
            List<Client> clients = null;
            try {
                clients = clientService.findAll();
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            List<Vehicle> vehicles = null;
            try {
                vehicles = vehicleService.findAll();
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            request.setAttribute("clients", clients);
            request.setAttribute("vehicles", vehicles);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        }
    }
}

