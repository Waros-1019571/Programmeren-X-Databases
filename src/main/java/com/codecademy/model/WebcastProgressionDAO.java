package com.codecademy.model;

import com.codecademy.entity.Student;
import com.codecademy.entity.Webcast;
import com.codecademy.entity.WebcastProgression;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class WebcastProgressionDAO implements DAO<WebcastProgression> {

    private final DBConnection dbConnection;

    public WebcastProgressionDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<WebcastProgression> getAll() {
        Statement statement = null;
        ResultSet result = null;
        List<WebcastProgression> webcastProgressionList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT p.StudentID, s.Name, p.WebcastID, w.Title, p.Progress FROM STUDENT_WEBCAST AS p JOIN STUDENT AS s ON s.ID = p.StudentID JOIN WEBCAST as w ON w.ID = p.WebcastID");

            while (result.next()) {
                WebcastProgression webcastProgression = new WebcastProgression();

                Student student = new Student();
                student.setId(result.getInt(1));
                student.setName(result.getString(2));

                Webcast webcast = new Webcast();
                webcast.setId(result.getInt(3));
                webcast.setTitle(result.getString(4));

                webcastProgression.setProgress(result.getInt(5));
                webcastProgressionList.add(webcastProgression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement,result);
        }
        return webcastProgressionList;
    }

    @Override
    public boolean create(WebcastProgression webcastProgression) {
        boolean result = false;
        PreparedStatement statement = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO STUDENT_WEBCAST (StudentID, WebcastID) VALUES(?,?)");
            statement.setInt(1, webcastProgression.getStudent().getId());
            statement.setInt(2, webcastProgression.getWebcast().getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoSuchElementException("Create failed: no rows affected.");
            }

            result = true;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement);
        }
        return result;
    }

    @Override
    public boolean update(WebcastProgression webcastProgression) {
        boolean result = false;
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE WEBCAST SET StudentID = ?, WebcastID = ? WHERE ID = ?");
            statement.setInt(1, webcastProgression.getStudent().getId());
            statement.setInt(2, webcastProgression.getWebcast().getId());

            if (statement.executeUpdate() == 0) {
                throw new NoSuchElementException("Update failed: no rows affected.");
            }
            result = true;
        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return result;
    }

    @Override
    public boolean delete(WebcastProgression webcastProgression) {
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            statement = connection.prepareStatement("DELETE FROM STUDENT_WEBCAST WHERE StudentID = ? AND WebcastID = ?");
            statement.setInt(1, webcastProgression.getStudent().getId());
            statement.setInt(2, webcastProgression.getWebcast().getId());
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        if (rowsDeleted == 0) {
            return false;
        }
        return true;
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
