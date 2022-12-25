package com.codecademy.model;

import com.codecademy.entity.Organisation;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class OrganisationDAO implements DAO<Organisation> {
    private final DBConnection dbConnection;

    public OrganisationDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Organisation> getAll() {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Organisation> organisationList = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM ORGANISATION");
            organisationList = new ArrayList<>();

            while (result.next()) {
                Organisation organisation = new Organisation();
                organisation.setOrganisationId(result.getInt(1));
                organisation.setName(result.getString(2));
                organisationList.add(organisation);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return organisationList;
    }

    @Override
    public Organisation get(long id) {
        Statement statement = null;
        ResultSet result = null;
        Organisation organisation = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM ORGANISATION WHERE id = ?");

            if (result.next()) {
                organisation = new Organisation();
                organisation.setOrganisationId(result.getInt(1));
                organisation.setName(result.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return organisation;
    }

    @Override
    public boolean create(Organisation organisation) {
        PreparedStatement statement = null;
        boolean isCreated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO ORGANISATION (Name) VALUES(?)");
                statement.setString(1, organisation.getName());
                isCreated = (statement.executeUpdate() > 0);

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeRequest(statement);
            }
            return isCreated;
    }

    @Override
    public boolean update(Organisation organisation) {
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("UPDATE Organisation SET Name = ? WHERE ID = ?");
            statement.setString(1, organisation.getName());
            statement.setInt(2, organisation.getOrganisationId());
            isUpdated = statement.executeUpdate() > 0;

        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        return isUpdated;
    }

    @Override
    public boolean delete(Organisation organisation)  {
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM ORGANISATION WHERE ID = ?");
            statement.setInt(1, organisation.getOrganisationId());
            isDeleted = statement.executeUpdate() > 0;

        } catch(SQLException e) {
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
