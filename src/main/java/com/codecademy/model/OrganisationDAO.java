package com.codecademy.model;

import com.codecademy.entity.Organisation;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class OrganisationDAO implements DAO<Organisation> {
    private DBConnection dbConnection;

    public OrganisationDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Organisation> getAll() throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Organisation> organisationList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM ORGANISATION");

            while (result.next()) {
                Organisation organisation = new Organisation();
                organisation.setOrganisationId(result.getInt(1));
                organisation.setName(result.getString(2));
                organisationList.add(organisation);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (result != null) {
                result.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
        return organisationList;
    }

    @Override
    public Optional<Organisation> get(long id) {
        return Optional.empty();
    }

    @Override
    public void create(Organisation organisation) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO ORGANISATION (Name) VALUES(?)");
            String name = organisation.getName();
            statement.setString(1, name);
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
    public void update(Organisation organisation) {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT count(*) FROM Organisation WHERE ID = ?");
            statement.setInt(1, organisation.getOrganisationId());

            result = statement.executeQuery();

            if (!result.next()){
                throw new NoSuchElementException("No results found");
            } else {
                statement = connection.prepareStatement("UPDATE Organisation SET Name = ? WHERE ID = ?");
                statement.setString(1, organisation.getName());
                statement.setInt(2, organisation.getOrganisationId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Update failed: no rows affected.");
                }
            }

        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();

        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void delete(Organisation organisation) {

    }
}
