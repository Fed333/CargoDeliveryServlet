package com.epam.cargo.dao.connection.pool;

import com.epam.cargo.infrastructure.annotation.PropertyValue;
import com.epam.cargo.infrastructure.annotation.Singleton;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base implementation of ConnectionPool interface.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
@Singleton(type = Singleton.Type.LAZY)
public class BasicConnectionPool
        implements ConnectionPool {

    @PropertyValue(property = "url")
    private String url;

    @PropertyValue(property = "username")
    private String user;

    @PropertyValue(property = "password")
    private String password;

    private List<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);;
    private List<Connection> usedConnections = new ArrayList<>();

    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 30;

    private static final int MAX_TIMEOUT = 10000;

    @SuppressWarnings("unused")
    public BasicConnectionPool() {}

    public BasicConnectionPool(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @PostConstruct
    public void init() throws SQLException {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(createConnection(url, user, password));
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        if (Objects.isNull(connection)){
            Logger logger = Logger.getLogger(BasicConnectionPool.class);
            logger.error("Null Connection received!");
        }
        Objects.requireNonNull(connection, "The obtained connection mustn't be null!");
        if(!connection.isValid(MAX_TIMEOUT)){
            connection = createConnection(url, user, password);
        }

        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

}