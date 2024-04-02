package com.epf.rentmanager.servlet;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/cars/modify")
public class VehicleModifyServlet extends HttpServlet {
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("id"));
            Vehicle vehicle = vehicleService.findById(vehicleId);
            request.setAttribute("vehicle", vehicle);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/modify.jsp").forward(request, response);
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error-page.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));

            vehicleService.modify(vehicleId, constructeur, modele, nbPlaces);
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
            Vehicle vehicle = null;
            try {
                vehicle = vehicleService.findById(vehicleId);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            request.setAttribute("vehicle", vehicle);
            request.setAttribute("error_nbr", e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/modify.jsp").forward(request, response);
        }
    }
}
