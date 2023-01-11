package com.codecademy.model;

import com.codecademy.entity.Course;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CourseDAO implements DAO<Course> {

    private DBConnection dbConnection;

    public CourseDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Course get(long id) {
        return null;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public boolean create(Course course) {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean isCreated = false;

        try {
            Connection connection = dbConnection.getConnection();

            statement = connection.prepareStatement("INSERT INTO COURSE (Name) VALUES(?)");
            String courseTitle = course.getTitle();

            statement.setString(1, courseTitle);
            statement.executeUpdate();
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoSuchElementException("Update failed: no rows affected.");
            }
            isCreated = true;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return isCreated;
    }

    @Override
    public boolean update(Course course) {
        return false;
    }
    @Override
    public boolean delete(Course course) {
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
