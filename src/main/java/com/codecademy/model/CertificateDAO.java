package com.codecademy.model;

import com.codecademy.entity.Certificate;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CertificateDAO implements DAO<Certificate> {

    private final DBConnection dbConnection;

    public CertificateDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Certificate> getAll() {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Certificate> certificateList = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM CERTIFICATE");
            certificateList = new ArrayList<>();

            while (result.next()) {
                Certificate certificate = new Certificate();
                certificate.setCertificateId(result.getInt(1));
                certificate.setGrade(result.getDouble(2));
                certificateList.add(certificate);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return certificateList;
    }

    @Override
    public boolean create(Certificate certificate) {
        PreparedStatement statement = null;
        boolean isCreated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO CERTIFICATE (Id) VALUES(?)");
            statement.setInt(1, certificate.getCertificateId());
            isCreated = (statement.executeUpdate() > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }
        return isCreated;
    }

    @Override
    public boolean update(Certificate certificate) {
        PreparedStatement statement = null;
        boolean isUpdated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("UPDATE CERTIFICATE SET Grade = ? WHERE Id = ?");
            statement.setDouble(1, certificate.getGrade());
            statement.setInt(2, certificate.getCertificateId());
            isUpdated = statement.executeUpdate() > 0;

        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            closeRequest(statement);
        }

        return isUpdated;
    }

    @Override
    public boolean delete(Certificate certificate) {
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM Certificate WHERE Id = ?");
            statement.setInt(1, certificate.getCertificateId());
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
