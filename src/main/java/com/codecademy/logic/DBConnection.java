package com.codecademy.logic;

import java.sql.*;

public class DBConnection {
    private Connection connection = null;
    private final String connectionUrl = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=White;user=white;password=Cheese;encrypt=true;trustServerCertificate=true;";// ;encrypt=true;trustServerCertificate=true"

    public DBConnection(){
        connect();
    }

    public void connect() {
        try {
            if (connection != null) {
                connection.close();
            }
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
