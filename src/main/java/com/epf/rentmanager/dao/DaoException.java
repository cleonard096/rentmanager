package com.epf.rentmanager.dao;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DaoException extends Exception {
    public DaoException(String message) {
        super(message);
    }
}

