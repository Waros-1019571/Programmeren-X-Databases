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
            result = statement.executeQuery("SELECT * FROM VOICE_ACTOR");

            while (result.next()) {
                VoiceActor voiceActor = new VoiceActor();
                voiceActor.setVoiceActorId(result.getInt(1));
                voiceActor.setVoiceActorName(result.getString(2));
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
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO VOICE_ACTOR (Name) VALUES(?)");
            String name = voiceActor.getName();
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
    public void update(VoiceActor voiceActor) {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT count(*) FROM VOICE_ACTOR WHERE ID = ?");
            statement.setInt(1, voiceActor.getVoiceActorId());

            result = statement.executeQuery();

            if (!result.next()){
                throw new NoSuchElementException("No results found");
            } else {
                statement = connection.prepareStatement("UPDATE VOICE_ACTOR SET Name = ? WHERE ID = ?");
                statement.setString(1, voiceActor.getName());
                statement.setInt(2, voiceActor.getVoiceActorId());
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
            statement.setInt(1, voiceActor.getVoiceActorId());
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
