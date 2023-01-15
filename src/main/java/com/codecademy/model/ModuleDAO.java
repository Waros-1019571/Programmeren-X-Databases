package com.codecademy.model;


import com.codecademy.entity.Course;
import com.codecademy.entity.Module;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
            result = statement.executeQuery("SELECT m.CourseID, c.Title, m.ID, m.Version, m.ContactName, m.ContactEmail, m.Title, m.Description, m.PublicationDate FROM MODULE AS m LEFT JOIN Course AS c ON m.CourseID = C.ID");
            moduleList = new ArrayList<>();

            while (result.next()) {
                Course course = new Course();
                Module module = new Module();

                course.setCourseId(result.getInt(1));
                course.setTitle(result.getString(2));
                module.setCourse(course);

                module.setID(3);
                module.setVersion(result.getDouble(4));
                module.setContactName(result.getString(5));
                module.setContactEmail(result.getString(6));
                module.setTitle(result.getString(7));
                module.setDescription(result.getString(8));
                module.setPublicationDate(result.getDate(9));
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
            statement = connection.prepareStatement("INSERT INTO MODULE (Title, Version, ContactName, ContactEmail, PublicationDate, CourseID, Description) VALUES (?, ?, ?, ?, ?, ?, ?)");
            putModuleInStatement(statement, module);
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
            statement = connection.prepareStatement("UPDATE MODULE SET Title = ?, Version = ?, ContactName = ?, ContactEmail = ?, PublicationDate = ?, CourseID = ?, Description = ? WHERE ID = ?");
            putModuleInStatement(statement, module);
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

    private void putModuleInStatement(PreparedStatement statement, Module module) throws SQLException {
        try {
            statement.setString(1, module.getTitle());
            statement.setDouble(2, module.getVersion());
            statement.setString(3, module.getContactName());
            statement.setString(4, module.getContactEmail());
            statement.setObject(5, module.getPublicationDate());
            statement.setObject(6, module.getCourse().getCourseId());
            statement.setString(7, module.getDescription());
        } catch (SQLException| NoSuchElementException e) {
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
