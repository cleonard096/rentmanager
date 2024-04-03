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

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("id"));
            Client client = clientService.findById(clientId);
            List<Reservation> reservations;
            reservations = reservationService.findResaByClientId(clientId);
            List<Vehicle> vehicles;
            vehicles = vehicleService.findByReservations(reservations);
            for (Reservation reservation : reservations) {
                Vehicle vehicle = vehicleService.findById(reservation.getVehicleId());
                String vehicleDescr = vehicle.getConstructeur() + " " + vehicle.getModele();
                reservation.setVehicleDescr(vehicleDescr);
            }
            request.setAttribute("reservations", reservations);
            request.setAttribute("client", client);
            request.setAttribute("vehicles", vehicles);
            request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error-page.jsp");
        }
    }

}
