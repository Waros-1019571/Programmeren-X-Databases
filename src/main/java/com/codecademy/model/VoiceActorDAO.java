package com.codecademy.model;

import com.codecademy.entity.Organisation;
import com.codecademy.entity.VoiceActor;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.*;

public class VoiceActorDAO implements DAO<VoiceActor> {
    private DBConnection dbConnection;

    public VoiceActorDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<VoiceActor> getAll() {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<VoiceActor> voiceActorList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT VOICE_ACTOR.Name, VOICE_ACTOR.ID, ORGANISATION.Name, ORGANISATION.ID FROM VOICE_ACTOR LEFT JOIN ORGANISATION ON VOICE_ACTOR.OrganisationID = ORGANISATION.ID");
            while (result.next()) {
                Organisation organisation = new Organisation();
                organisation.setOrganisationId(result.getInt(4));
                organisation.setName(result.getString(3));

                VoiceActor voiceActor = new VoiceActor();
                voiceActor.setId(result.getInt(2));
                voiceActor.setName(result.getString(1));
                voiceActor.setOrganisation(organisation);

                voiceActorList.add(voiceActor);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return voiceActorList;
    }

    @Override
    public Optional<VoiceActor> get(long id) {
        return Optional.empty();
    }

    @Override
    public boolean create(VoiceActor voiceActor) {
        PreparedStatement statement = null;
        boolean result = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO VOICE_ACTOR (Name, OrganisationID) VALUES(?,?)");
            String name = voiceActor.getName();
            statement.setString(1, name);
            int organisationId = voiceActor.getOrganisation().getOrganisationId();
            statement.setInt(2, organisationId);
            result = statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement);
        }
        return result;
    }

    @Override
    public boolean update(VoiceActor voiceActor) {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean isUpdated = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT count(*) FROM VOICE_ACTOR WHERE ID = ?");
            statement.setInt(1, voiceActor.getId());

            result = statement.executeQuery();

            if (!result.next()){
                throw new NoSuchElementException("No results found");
            } else {
                statement = connection.prepareStatement("UPDATE VOICE_ACTOR SET Name = ?, OrganisationID = ? WHERE ID = ?");
                statement.setString(1, voiceActor.getName());
                statement.setInt(2, voiceActor.getOrganisation().getOrganisationId());
                statement.setInt(3, voiceActor.getId());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Update failed: no rows affected.");
                }
            }
            isUpdated = true;

        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();

        } finally {
            closeRequest(statement, result);
        }
        return isUpdated;
    }

    @Override
    public boolean delete(VoiceActor voiceActor) {
        PreparedStatement statement = null;
        boolean isDeleted = false;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM VOICE_ACTOR WHERE ID = ?");
            statement.setInt(1, voiceActor.getId());
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
