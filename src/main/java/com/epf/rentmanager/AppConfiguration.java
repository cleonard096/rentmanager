package com.epf.rentmanager;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@ComponentScan({ "com.epf.rentmanager.service", "com.epf.rentmanager.dao","com.epf.rentmanager.persistence" })
public class AppConfiguration {
    private final ClientService clientService;
    private final VehicleService vehicleService;
    private final ReservationService reservationService;
    @Autowired
    public AppConfiguration(ClientService clientService, VehicleService vehicleService, ReservationService reservationService) {
        this.clientService = clientService;
        this.vehicleService = vehicleService;
        this.reservationService = reservationService;
    }

    @PostConstruct
    public void init() {
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }
}

