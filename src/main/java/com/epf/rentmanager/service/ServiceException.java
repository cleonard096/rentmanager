package com.epf.rentmanager.service;
import com.epf.rentmanager.dao.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceException extends Exception {
    public ServiceException(String message, DaoException e) {
        super(message,e);
    }
}
