package com.codecademy.controller;

import com.codecademy.CodecademyApplication;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.OrganisationDAO;
import com.codecademy.model.VoiceActorDAO;
import com.codecademy.model.WebcastDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CodecademyController {
    private DBConnection dbConnection = new DBConnection();

    @FXML
    private GridPane organisationPane;
    @FXML
    private GridPane voiceActorPane;
    @FXML
    private Pane webcastPane;
    @FXML
    private Pane studentPane;
    @FXML
    private Pane coursePane;

    @FXML
    public void initialize() {
        loadController("Course-view.fxml", new CourseController(), coursePane);
        loadController("Organisation-view.fxml", new OrganisationController(), organisationPane);
        loadController("voiceActor-view.fxml", new VoiceActorController(), voiceActorPane);
        loadController("Webcast-view.fxml", new WebcastController(), webcastPane);
        loadController("Student-view.fxml", new StudentController(), studentPane);
    }

    private void loadController(String recourseName, Controller controller, Pane pane) {
        FXMLLoader loader = new FXMLLoader(CodecademyApplication.class.getResource(recourseName));
        loader.setControllerFactory(newController -> {
            controller.setDBConnection(dbConnection);
            return controller;
        });

        try {
            pane.getChildren().clear();
            pane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
