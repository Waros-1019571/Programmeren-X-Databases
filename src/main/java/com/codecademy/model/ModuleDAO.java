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
            result = statement.executeQuery("SELECT ID, Version, ContactName, ContactEmail, Title, Description, PublicationDate, CourseID FROM MODULE");
            moduleList = new ArrayList<>();

            while (result.next()) {
                Module module = new Module();
                module.setCourseID(result.getInt(1));
                module.setVersion(result.getString(2));
                module.setContactName(result.getString(3));
                module.setContactEmail(result.getString(4));
                module.setTitle(result.getString(5));
                module.setDescription(result.getString(6));
                module.setPublicationDate(result.getDate(7));
                module.setCourseID(result.getInt(8));
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
            statement = connection.prepareStatement("INSERT INTO MODULE (Version, ContactName, ContactEmail, Title, Description, PublicationDate, CourseID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, module.getVersion());
            statement.setString(2, module.getContactName());
            statement.setString(3, module.getContactEmail());
            statement.setString(4, module.getTitle());
            statement.setString(5, module.getDescription());
            statement.setObject(6, module.getPublicationDate());
            statement.setObject(7, module.getCourseID());
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
            statement = connection.prepareStatement("UPDATE MODULE SET Version = ?,ContactName = ?,ContactEmail = ?,Title = ?,Description = ?,PublicationDate = ?,CourseID = ? WHERE ID = ?");
            statement.setString(1, module.getVersion());
            statement.setString(2, module.getContactName());
            statement.setString(3, module.getContactEmail());
            statement.setString(4, module.getTitle());
            statement.setString(5, module.getDescription());
            statement.setObject(6, module.getPublicationDate());
            statement.setObject(7, module.getCourseID());
            statement.setObject(8, module.getID());
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

        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM MODULE WHERE ID = ?");
            statement.setInt(1, module.getID());
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
