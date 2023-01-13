package com.codecademy.model;


import com.codecademy.entity.Module;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAO implements DAO<Module> {

    private final DBConnection dbConnection;

    public ModuleDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Module> getAll() {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Module> studentList = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT ID, SerialNumber, Version, ContactName, ContactEmail, Title, Description, PublicationDate, CourseID FROM MODULE");
            studentList = new ArrayList<>();

            while (result.next()) {
                Module module = new Module();
                module.setCourseID(result.getInt(1));
                module.setSerialNumber(result.getString(2));
//                student.setId(result.getInt(1));
//                student.setEmail(result.getString(2));
//                student.setName(result.getString(3));
//                student.setBirthDate(result.getDate(4));
//                student.setGender(result.getInt(5));
//                student.setAddress(result.getString(6));
//                student.setPostalCode(result.getString(7));
//                student.setCity(result.getString(8));
//                student.setCountry(result.getString(9));
//                studentList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
//            closeRequest(statement, result);
        }
        return studentList;
    }

    @Override
    public boolean create(Module module) {
        return false;

        //INSERT INTO MODULE (SerialNumber, Version, ContactName, ContactEmail, Title, Description, PublicationDate, CourseID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
    }

    @Override
    public boolean update(Module module) {
        return false;
    }

    @Override
    public boolean delete(Module module) {
        return false;
    }
}
