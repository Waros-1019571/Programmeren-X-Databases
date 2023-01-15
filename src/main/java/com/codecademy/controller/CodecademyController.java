package com.codecademy.controller;

import com.codecademy.CodecademyApplication;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
    private Pane modulePane;
    @FXML
    private Pane statisticsPane;
    @FXML
    private Pane enrollmentPane;

    @FXML
    public void initialize() {
        loadController("Course-view.fxml", new CourseController(), coursePane);
        loadController("Organisation-view.fxml", new OrganisationController(), organisationPane);
        loadController("voiceActor-view.fxml", new VoiceActorController(), voiceActorPane);
        loadController("Webcast-view.fxml", new WebcastController(), webcastPane);
        loadController("Student-view.fxml", new StudentController(), studentPane);
        loadController("Module-view.fxml", new ModuleController(), modulePane);
        loadController("StudentProgressionview.fxml", new StudentProgressionController(), statisticsPane);
        loadController("Enrollment-view.fxml", new EnrollmentController(), enrollmentPane);
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
