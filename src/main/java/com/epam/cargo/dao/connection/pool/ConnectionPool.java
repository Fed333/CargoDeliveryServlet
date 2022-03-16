package com.epam.cargo.dao.connection.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface of connection pool.<br>
 * Declares base pool functionality.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public interface ConnectionPool {
    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
}
