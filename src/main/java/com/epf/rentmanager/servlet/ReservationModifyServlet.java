package com.epf.rentmanager.servlet;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/modify")
public class ReservationModifyServlet extends HttpServlet {

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
            long reservationId = Long.parseLong(request.getParameter("id"));
            Reservation reservation = reservationService.findById(reservationId);
            request.setAttribute("reservation", reservation);
            request.getRequestDispatcher("/WEB-INF/views/rents/modify.jsp").forward(request, response);
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
            response.sendRedirect(request.getContextPath() + "/error-page.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long reservationId = Long.parseLong(request.getParameter("reservationId")); // Correction du nom du paramètre
            LocalDate newDebut = LocalDate.parse(request.getParameter("newDebut"));
            LocalDate newFin = LocalDate.parse(request.getParameter("newFin"));

            reservationService.modify(reservationId, newDebut, newFin);
            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            long reservationId = Long.parseLong(request.getParameter("reservationId"));
            Reservation reservation = null;
            try {
                reservation = reservationService.findById(reservationId);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            request.setAttribute("reservation", reservation);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/rents/modify.jsp").forward(request, response);
        }
    }
}
