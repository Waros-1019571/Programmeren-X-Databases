package com.codecademy.logic;

import java.sql.*;

public abstract class DBConnection {

    protected Connection connection;

    String connectionUrl = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=White;user=white;password=Cheese;encrypt=true;trustServerCertificate=true;";// ;encrypt=true;trustServerCertificate=true"

    public DBConnection(){
        connect();
    }

    public void connect(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
        }catch(SQLException|ClassNotFoundException e){
            System.out.println("ERROR connecting to database!");
            System.out.println(e.toString());
        }
    }

    public abstract ResultSet select(String query);

    public abstract boolean update(String query);

    public abstract boolean delete(String query);

    public void close(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println("ERROR while closing connections!");
            System.out.println(e.toString());
        }
    }
}
