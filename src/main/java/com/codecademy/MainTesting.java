package com.codecademy;

import com.codecademy.entity.Organisation;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.OrganisationDAO;

import java.sql.SQLException;

public class MainTesting {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);
        Organisation organisation = new Organisation();
        organisation.setName("OrganisationName1");
        try {
            organisationDAO.create(organisation);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
