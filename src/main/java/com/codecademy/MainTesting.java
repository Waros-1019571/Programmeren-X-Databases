package com.codecademy;

import com.codecademy.entity.Organisation;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.OrganisationDAO;

import java.sql.Array;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class MainTesting {

    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);
        Organisation organisation = new Organisation();
        organisation.setName("OrganisationName1");
        organisation.setOrganisationId(1);

        try {

            organisationDAO.update(organisation);
        } catch (NoSuchElementException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }

    }
}
