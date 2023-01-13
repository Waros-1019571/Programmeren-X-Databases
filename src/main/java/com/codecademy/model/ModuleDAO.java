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
        ArrayList<Module> moduleList = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT ID, SerialNumber, Version, ContactName, ContactEmail, Title, Description, PublicationDate, CourseID FROM MODULE");
            moduleList = new ArrayList<>();

            while (result.next()) {
                Module module = new Module();
                module.setCourseID(result.getInt(1));
                module.setSerialNumber(result.getString(2));
                module.setVersion(result.getString(3));
                module.setContactName(result.getString(4));
                module.setContactEmail(result.getString(5));
                module.setTitle(result.getString(6));
                module.setDescription(result.getString(7));
                module.setPublicationDate(result.getDate(8));
                module.setCourseID(result.getInt(9));
                moduleList.add(module);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
//            closeRequest(statement, result);
        }
        return moduleList;
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
