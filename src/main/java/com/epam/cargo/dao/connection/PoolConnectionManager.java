package com.epam.cargo.dao.connection;

import com.epam.cargo.dao.connection.pool.BasicConnectionPool;
import com.epam.cargo.dao.connection.pool.ConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PoolConnectionManager {
    private final Properties property = new Properties();

    public PoolConnectionManager(FileInputStream fis) throws IOException {
        property.load(fis);
    }

    public ConnectionPool getConnectionPool(){
        ConnectionPool connectionPool = null;

        try{
            connectionPool = BasicConnectionPool.create(
                    property.getProperty("url"),
                    property.getProperty("username"),
                    property.getProperty("password")
            );
        }
        catch (SQLException e){
            System.err.println("Failed connectionPool");
            e.printStackTrace();
        }
        return connectionPool;
    }
}
