package com.codecademy.model;

import com.codecademy.entity.Course;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements DAO<Course> {

    private DBConnection dbConnection;

    public CourseDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Course get(long id) {
        return null;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public void create(Course course) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();

            statement = connection.prepareStatement("INSERT INTO ORGANISATION (Name) VALUES(?)");
            String courseTitle = course.getTitle();

            statement.setString(1, courseTitle);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public boolean update(Course course) {
        return false;
    }
    @Override
    public boolean delete(Course course) {
        return false;
    }
}
