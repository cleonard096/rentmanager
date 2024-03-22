package com.epf.rentmanager.servlet;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
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
			int userCount = clientService.count();
			int vehicleCount = vehicleService.count();
			int reservationCount = reservationService.count();

			request.setAttribute("userCount", userCount);
			request.setAttribute("vehicleCount", vehicleCount);
			request.setAttribute("reservationCount", reservationCount);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "Error: " + e.getMessage());
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
		}
	}
}
