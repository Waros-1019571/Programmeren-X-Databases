package com.codecademy.controller;

import com.codecademy.CodecademyApplication;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.OrganisationDAO;
import com.codecademy.model.VoiceActorDAO;
import com.codecademy.model.WebcastDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CodecademyController {
    private DBConnection dbConnection = new DBConnection();
    private OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);
    private CourseDAO courseDAO = new CourseDAO(dbConnection);
    private VoiceActorDAO voiceActorDAO = new VoiceActorDAO(dbConnection);
    private WebcastDAO webcastDAO = new WebcastDAO(dbConnection, voiceActorDAO);

    @FXML
    private GridPane organisationPane;
    @FXML
    private Tab organisationTab;
    @FXML
    private GridPane voiceActorPane;
    @FXML
    private Tab voiceActorTab;
    @FXML
    private Pane webcastPane;
    @FXML
    private Tab webcastTab;
    @FXML
    private TabPane tabPane;

    @FXML
    public void initialize() {
        loadOrganisation();
        loadWebcasts();
        // reload panes when selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(organisationTab)) {
                loadOrganisation();
            }
            if (newValue.equals(voiceActorTab)) {
                loadVoiceActor();
            }
            if (newValue.equals(webcastTab)) {
                loadVoiceActor();
            }
        });
    }

    private void loadOrganisation() {
        FXMLLoader loader = new FXMLLoader(CodecademyApplication.class.getResource("Organisation-Overview-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationOverviewController controller = new OrganisationOverviewController();
            controller.setOrganisationDAO(organisationDAO);
            controller.setRoot(organisationPane);
            return controller;
        });

        try {
            organisationPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVoiceActor() {
        FXMLLoader loader = new FXMLLoader(CodecademyApplication.class.getResource("voiceActor-view.fxml"));
        loader.setControllerFactory(newController -> {
            VoiceActorController controller = new VoiceActorController();
            controller.setVoiceActorDAO(voiceActorDAO);
            controller.setOrganisationDAO(organisationDAO);
            return controller;
        });

        try {
            voiceActorPane.getChildren().clear();
            voiceActorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadWebcasts() {
        FXMLLoader loader = new FXMLLoader(CodecademyApplication.class.getResource("Webcast-view.fxml"));
        loader.setControllerFactory(newController -> {
            WebcastController controller = new WebcastController();
            controller.setVoiceActorDAO(voiceActorDAO);
            controller.setWebcastDAO(webcastDAO);
            return controller;
        });

        try {
            webcastPane.getChildren().clear();
            webcastPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
