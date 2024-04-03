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
import java.util.List;

@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("id"));
            Vehicle vehicle = vehicleService.findById(vehicleId);
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleId);
            List<Client> clients = clientService.findByReservations(reservations);
            for (Reservation reservation : reservations) {
                Client client = clientService.findById(reservation.getClientId());
                String clientFullName = client.getNom() + " " + client.getPrenom();
                reservation.setClientFullName(clientFullName);
            }
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("clients", clients);
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error-page.jsp");
        }
    }
}
