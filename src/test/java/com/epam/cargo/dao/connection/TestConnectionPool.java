package com.epam.cargo.dao.connection;

import com.epam.cargo.dao.connection.pool.ConnectionPool;
import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton(type = Singleton.Type.LAZY)
public class TestConnectionPool implements ConnectionPool {

    private Connection testConnection;

    @PropertyValue
    private String url;

    @PropertyValue
    private String username;

    @PropertyValue
    private String password;

    @PostConstruct
    private void init() throws SQLException {
        testConnection = DriverManager.getConnection(
          url,
          username,
          password
        );
        testConnection.setAutoCommit(false);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return testConnection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        return false;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
