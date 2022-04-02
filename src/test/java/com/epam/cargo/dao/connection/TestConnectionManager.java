package com.epam.cargo.dao.connection;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnectionManager {
    Properties property = new Properties();

    public TestConnectionManager() throws IOException {
        this.property = new Properties();
        property.load(new FileInputStream("src/test/resources/application.properties"));
    }

    private TestConnectionManager(InputStream is) throws IOException {
        property.load(is);
    }

    public Connection getConnection(){
        Connection connection = null;

        try{
            connection = DriverManager.getConnection(
                    property.getProperty("url"),
                    property.getProperty("username"),
                    property.getProperty("password")
            );
        }
        catch (SQLException e){
            System.err.println("Failed connection");
            e.printStackTrace();
        }
        return connection;
    }
}
