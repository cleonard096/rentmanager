package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
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
import java.util.Objects;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String last_name = request.getParameter("last_name");
        String first_name = request.getParameter("first_name");
        String email = request.getParameter("email");
        LocalDate birthdate = LocalDate.parse(request.getParameter("birthdate"));
        Client newClient = new Client(last_name, first_name, email, birthdate);
        try {
            clientService.create(newClient);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (ServiceException | NumberFormatException e) {
            e.printStackTrace();
            if (Objects.equals(e.getMessage(), "L'adresse email est déjà attribué à un client.")){
                request.setAttribute("error_mail", e.getMessage());
            }
            if (Objects.equals(e.getMessage(), "Le client doit avoir au moins 18 ans pour être enregistré.")){
                request.setAttribute("error_age", e.getMessage());
            }
            if (Objects.equals(e.getMessage(), "Le nom  du client doit contenir au moins 3 caracteres.")){
                request.setAttribute("error_nom", e.getMessage());
            }
            if (Objects.equals(e.getMessage(), "Le prénom du client doit contenir au moins 3 caracteres.")){
                request.setAttribute("error_prenom", e.getMessage());
            }
            long clientId = Long.parseLong(request.getParameter("clientId"));
            Client client = null;
            try {
                client = clientService.findById(clientId);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            request.setAttribute("client", client);
            request.getRequestDispatcher("/WEB-INF/views/users/modify.jsp").forward(request, response);
        }

    }
}
