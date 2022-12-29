package com.codecademy.model;

import com.codecademy.entity.VoiceActor;
import com.codecademy.entity.Webcast;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WebcastDAO implements DAO<Webcast> {
    private final DBConnection dbConnection;

    public WebcastDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    private void addVoiceActor(Webcast webcast, int voiceActorID) throws SQLException {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("SELECT ID, Name, OrganisationID FROM VOICE_ACTOR WHERE ID = (?)");
            statement.setInt(1, voiceActorID);
            result = statement.executeQuery();

            VoiceActor voiceActor = new VoiceActor();
            voiceActor.setId(result.getInt(1));
            voiceActor.setName(result.getString(2));
            voiceActor.setOrganisationId(result.getInt(3));
            webcast.setVoiceActor(voiceActor);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
        }

    }

    private void addCourse(Webcast webcast, int courseID) {
        // TODO
    }

    @Override
    public Optional get(long id) {
        // TODO
        return Optional.empty();
    }

    @Override
    public List<Webcast> getAll() throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Webcast> webcastList = new ArrayList<>();

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT ID, CourseID, VoiceActorID, Title, Description, URL, PublicationDate, Duration FROM WEBCAST");

            while (result.next()) {
                Webcast webcast = new Webcast();
                webcast.setId(result.getInt(1));
                addCourse(webcast, result.getInt(2));
                addVoiceActor(webcast, result.getInt(3));
                webcast.setTitle(result.getString(4));
                webcast.setDescription(result.getString(5));
                webcast.setUrl(result.getString(6));
                webcast.setPublicationDate(result.getDate(7));
                webcast.setDuration(result.getInt(8));
                webcastList.add(webcast);
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
        return webcastList;
    }

    @Override
    public void create(Webcast webcast) throws SQLException {
        // TODO
    }

    @Override
    public void update(Webcast webcast) {
        // TODO
    }

    @Override
    public boolean delete(Webcast webcast) throws SQLException {
        // TODO
        return false;
    }
}
