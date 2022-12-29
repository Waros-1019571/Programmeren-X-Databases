package com.codecademy.model;

import com.codecademy.entity.VoiceActor;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class VoiceActorDAO implements DAO<VoiceActor> {
    private DBConnection dbConnection;

    public VoiceActorDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<VoiceActor> getAll() throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<VoiceActor> voiceActorList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT ID, Name, OrganisationID FROM VOICE_ACTOR");

            while (result.next()) {
                VoiceActor voiceActor = new VoiceActor();
                voiceActor.setId(result.getInt(1));
                voiceActor.setName(result.getString(2));
                voiceActor.setOrganisationId(result.getInt(3));
                voiceActorList.add(voiceActor);
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
        return voiceActorList;
    }

    @Override
    public Optional<VoiceActor> get(long id) {
        return Optional.empty();
    }

    @Override
    public void create(VoiceActor voiceActor) throws SQLException {
        PreparedStatement statement = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO VOICE_ACTOR (Name, OrganisationID) VALUES(?,?)");
            String name = voiceActor.getName();
            statement.setString(1, name);
            int organisationId = voiceActor.getOrganisationId();
            statement.setInt(2, organisationId);
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
    public void update(VoiceActor voiceActor) {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT count(*) FROM VOICE_ACTOR WHERE ID = ?");
            statement.setInt(1, voiceActor.getId());

            result = statement.executeQuery();

            if (!result.next()){
                throw new NoSuchElementException("No results found");
            } else {
                statement = connection.prepareStatement("UPDATE VOICE_ACTOR SET Name = ? WHERE ID = ?");
                statement.setString(1, voiceActor.getName());
                statement.setInt(2, voiceActor.getId());
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
    public boolean delete(VoiceActor voiceActor) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList<VoiceActor> voiceActorList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("DELETE FROM VOICE_ACTOR WHERE ID = ?");
            statement.setInt(1, voiceActor.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }

        } catch(SQLException e) {
            e.printStackTrace();

        } finally {
            if (result != null) {
                result.close();
            }

            if (statement != null) {
                statement.close();
            }
        }
        return false;
    }
}
