package com.codecademy.model;

import com.codecademy.entity.VoiceActor;
import com.codecademy.entity.Webcast;
import com.codecademy.logic.DAO;
import com.codecademy.logic.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
            statement = connection.prepareStatement("SELECT ID, Name, OrganisationID FROM VOICE_ACTOR WHERE ID = ?");
            statement.setInt(1, voiceActorID);
            result = statement.executeQuery();

            if (result.next()) {
                VoiceActor voiceActor = new VoiceActor();
                voiceActor.setId(result.getInt(1));
                voiceActor.setName(result.getString(2));
                voiceActor.setOrganisationId(result.getInt(3));
                webcast.setVoiceActor(voiceActor);
            }
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
        PreparedStatement statement = null;

        try {
            Connection connection = dbConnection.getConnection();
            statement = connection.prepareStatement("INSERT INTO WEBCAST (CourseID, VoiceActorID, Title, Description, URL, PublicationDate, Duration) VALUES(?,?,?,?,?,?,?)");
            statement.setInt(1, webcast.getCourse().getCourseId());
            statement.setInt(2, webcast.getVoiceActor().getId());
            statement.setString(3, webcast.getTitle());
            statement.setString(4, webcast.getDescription());
            statement.setString(5, webcast.getUrl());
            statement.setDate(6, new Date(webcast.getPublicationDate().getTime())); // util date to sql date
            statement.setInt(7, webcast.getDuration());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public void update(Webcast webcast) {
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("UPDATE WEBCAST SET CourseID = ?, VoiceActorID = ?, Title = ?, Description = ?, URL = ?, PublicationDate = ?, Duration = ? WHERE ID = ?");
            statement.setInt(1, webcast.getCourse().getCourseId());
            statement.setInt(2, webcast.getVoiceActor().getId());
            statement.setString(3, webcast.getTitle());
            statement.setString(4, webcast.getDescription());
            statement.setString(5, webcast.getUrl());
            statement.setDate(6, new Date(webcast.getPublicationDate().getTime())); // util date to sql date
            statement.setInt(7, webcast.getDuration());
            statement.setInt(8, webcast.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NoSuchElementException("Update failed: no rows affected.");
            }
        } catch (SQLException | NoSuchElementException e) {
            e.printStackTrace();
        } finally {
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
    public boolean delete(Webcast webcast) throws SQLException {
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = null;
        int rowsDeleted = 0;

        try {
            statement = connection.prepareStatement("DELETE FROM WEBCAST WHERE ID = ?");
            statement.setInt(1, webcast.getId());
            rowsDeleted = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        if (rowsDeleted == 0) {
            return false;
        }
        return true;
    }
}
