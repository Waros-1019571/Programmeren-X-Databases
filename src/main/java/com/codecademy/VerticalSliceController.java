package com.codecademy;

import com.codecademy.controller.OrganisationController;
import com.codecademy.logic.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class VerticalSliceController {
    @FXML
    Pane root;

    @FXML
    public void initialize() {
        DBConnection dbConnection = new DBConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Organisation-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationController controller = new OrganisationController();
            controller.setDBConnection(dbConnection);
            return controller;
        });

        try {
            root.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
