package com.codecademy.model;

import com.codecademy.entity.Student;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StudentDAO implements DAO<Student> {

    private final DBConnection dbConnection;

    public StudentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Student> getAll() {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Student> studentList = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM STUDENT");
            studentList = new ArrayList<>();

            while (result.next()) {
                Student student = new Student();
                student.setEmail(result.getString(1));
                student.setName(result.getString(2));
                student.setBirthDate(result.getDate(3));
                student.setGender(result.getString(4));
                student.setStreet(result.getString(5));
                student.setPostalCode(result.getString(6));
                student.setHouseNumber(result.getString(7));
                student.setCity(result.getString(8));
                student.setCountry(result.getString(9));
                studentList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return studentList;
    }

    @Override
    public boolean create(Student student) {
        PreparedStatement statement = null;
        boolean isCreated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO STUDENT (email) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, student.getEmail());
            statement.setString(2, student.getName());
            statement.setDate(3, (Date) student.getBirthDate());
            statement.setString(4, student.getGender());
            statement.setString(5, student.getStreet());
            statement.setString(6, student.getPostalCode());
            statement.setString(7, student.getHouseNumber());
            statement.setString(8, student.getCity());
            statement.setString(9, student.getCountry());
            isCreated = (statement.executeUpdate() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return isCreated;
    }

    @Override
    public boolean update(Student student) {
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("UPDATE STUDENT SET name = ?, birthDate = ?, gender = ?, street = ?, postalCode = ?, houseNumber = ?, city = ?, country = ?  WHERE email = ?");
            statement.setString(1, student.getEmail());
            statement.setString(2, student.getName());
            statement.setDate(3, (Date) student.getBirthDate());
            statement.setString(4, student.getGender());
            statement.setString(5, student.getStreet());
            statement.setString(6, student.getPostalCode());
            statement.setString(7, student.getHouseNumber());
            statement.setString(8, student.getCity());
            statement.setString(9, student.getCountry());

            isUpdated = statement.executeUpdate() > 0;

        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        return isUpdated;
    }

    @Override
    public boolean delete(Student student) {
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM STUDENT WHERE email = ?");
            statement.setString(1, student.getEmail());
            isDeleted = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        return isDeleted;
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
