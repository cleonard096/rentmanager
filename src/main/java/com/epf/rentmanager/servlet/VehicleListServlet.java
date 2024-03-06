package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final VehicleService vehicleService;

    public VehicleListServlet() {
        super();
        this.vehicleService = VehicleService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
            request.setAttribute("vehicles", vehicles);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new ServletException("An error occurred while retrieving the vehicle list", e);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
            Vehicle vehicle =new Vehicle();
            vehicle = vehicleService.findById(vehicleId);
            vehicleService.delete(vehicle);
            response.sendRedirect(request.getContextPath() + "/cars");
        } catch (NumberFormatException | ServiceException e) {
            throw new ServletException("An error occurred while deleting the vehicle", e);
        }
    }
}


