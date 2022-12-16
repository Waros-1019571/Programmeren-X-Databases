package com.codecademy.model;

import com.codecademy.entity.Organisation;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganisationDAO  extends DBConnection implements DAO<Organisation> {

    @Override
    public Optional<Organisation> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Organisation> getAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM ORGANISATION");
            ArrayList<Organisation> organisations = new ArrayList<>();
            while (result.next()) {
                Organisation organisation = new Organisation();
                organisation.setOrganisationId(Integer.parseInt(result.getString(1)));
                organisation.setName(result.getString(2));
                organisations.add(organisation);
            }
            return organisations;

        } catch(SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public void save(Organisation organisation) {

    }

    @Override
    public void update(Organisation organisation, String[] params) {

    }

    @Override
    public void delete(Organisation organisation) {

    }

    @Override
    public ResultSet select(String query) {
        return null;
    }

    @Override
    public boolean update(String query) {
        return false;
    }

    @Override
    public boolean delete(String query) {
        return false;
    }
}
