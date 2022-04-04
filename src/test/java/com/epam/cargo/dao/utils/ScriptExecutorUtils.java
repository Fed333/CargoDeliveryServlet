package com.epam.cargo.dao.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Class designed for execution SQL scripts files.<br>
 * @author Roman Kovalchuk
 * @version 1.0
 * */
public class ScriptExecutorUtils {

    public static void executeSQLScript(Connection connection, String fileName) throws SQLException, FileNotFoundException {
        executeSQL(connection, getQueryFromFile(fileName));
    }

    public static ResultSet executeSelectSQLScript(Connection connection, String fileName) throws FileNotFoundException, SQLException {
        return executeSelectSQLQuery(connection, getQueryFromFile(fileName));
    }

    @NotNull
    private static String getQueryFromFile(String fileName) throws FileNotFoundException {
        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }

    public static void executeSQL(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(query);

    }

    public static ResultSet executeSelectSQLQuery(Connection connection, String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }

}
