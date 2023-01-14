package com.codecademy.model;

import com.codecademy.entity.Webcast;
import com.codecademy.entity.WebcastProgression;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class WebcastProgressionDAO implements DAO<WebcastProgression> {

    private final DBConnection dbConnection;

    public WebcastProgressionDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<WebcastProgression> getAll() {
        Statement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();

            result = statement.executeQuery("");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement,result);
        }
        return null;
    }

    @Override
    public boolean create(WebcastProgression webcast) {
        return false;
    }

    @Override
    public boolean update(WebcastProgression webcast) {
        return false;
    }

    @Override
    public boolean delete(WebcastProgression webcast) {
        return false;
    }

    private void closeRequest(Statement statement, ResultSet resultSet) {
        closeRequest(statement);
        try {
            resultSet.close();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void closeRequest(Statement statement) {
        try {
            statement.close();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
