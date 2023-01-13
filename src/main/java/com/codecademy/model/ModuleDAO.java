package com.codecademy.model;


import com.codecademy.entity.Module;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
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
            closeRequest(statement, result);
        }
        return moduleList;
    }

    @Override
    public boolean create(Module module) {
        PreparedStatement statement = null;
        boolean isCreated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO MODULE (SerialNumber, Version, ContactName, ContactEmail, Title, Description, PublicationDate, CourseID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, module.getSerialNumber());
            statement.setString(2, module.getVersion());
            statement.setString(3, module.getContactName());
            statement.setString(4, module.getContactEmail());
            statement.setString(5, module.getTitle());
            statement.setString(6, module.getDescription());
            statement.setObject(7, module.getPublicationDate());
            statement.setObject(8, module.getCourseID());
            isCreated = (statement.executeUpdate() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return isCreated;
    }

    @Override
    public boolean update(Module module) {
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("UPDATE MODULE SET SerialNumber = ?,Version = ?,ContactName = ?,ContactEmail = ?,Title = ?,Description = ?,PublicationDate = ?,CourseID = ? WHERE CourseID = ?");
            statement.setString(1, module.getSerialNumber());
            statement.setString(2, module.getVersion());
            statement.setString(3, module.getContactName());
            statement.setString(4, module.getContactEmail());
            statement.setString(5, module.getTitle());
            statement.setString(6, module.getDescription());
            statement.setObject(7, module.getPublicationDate());
            statement.setObject(8, module.getCourseID());
            statement.setObject(9, module.getCourseID());
            isUpdated = (statement.executeUpdate() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return isUpdated;
    }

    @Override
    public boolean delete(Module module) {
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
