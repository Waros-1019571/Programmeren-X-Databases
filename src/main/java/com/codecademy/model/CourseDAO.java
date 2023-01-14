package com.codecademy.model;

import com.codecademy.entity.Course;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CourseDAO implements DAO<Course> {

    private DBConnection dbConnection;

    public CourseDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Course> getAll() {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Course> courseList = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT ID, Title, Topic, CourseOwner, Name, Description, CourseLevel  FROM COURSE");
            courseList = new ArrayList<>();

            while (result.next()) {
                Course course = new Course();
                course.setCourseId(result.getInt(1));
                course.setTitle(result.getString(2));
                course.setTopic(result.getString(3));
                course.setOwnerEmail(result.getString(4));
                course.setOwnerName(result.getString(5));
                course.setDescription(result.getString(6));
                course.setCourseLevel(result.getInt(7));

                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement, result);
        }
        return courseList;
    }

    @Override
    public boolean create(Course course) {
        PreparedStatement statement = null;
        boolean isCreated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO COURSE (Title, Topic, CourseOwner, Name, Description, CourseLevel, CourseStatus) VALUES(?,?,?,?,?,?,?)");
            putCourseInStatementWithoutID(statement, course);
            isCreated = statement.executeUpdate() == 0;
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement);
        }
        return isCreated;
    }

    @Override
    public boolean update(Course course) {
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("UPDATE COURSE SET Title = ?, Topic = ?, CourseOwner = ?, Name = ?, Description = ?,CourseLevel = ?, CourseStatus = ? WHERE ID = ?");
            putCourseInStatementWithoutID(statement, course);
            statement.setInt(8, course.getCourseId());
            isUpdated = statement.executeUpdate() > 0;
        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        return isUpdated;
    }

    @Override
    public boolean delete(Course course) {
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM Course WHERE Id = ?");
            statement.setInt(1, course.getCourseId());
            isDeleted = statement.executeUpdate() > 0;
        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        return isDeleted;
    }

    private void putCourseInStatementWithoutID(PreparedStatement statement, Course course) {
        try {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getTopic());
            statement.setString(3, course.getOwnerEmail());
            statement.setString(4, course.getOwnerName());
            statement.setString(5, course.getDescription());
            statement.setInt(6, course.getCourseLevel());
            statement.setInt(7, 0);
        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        }
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
