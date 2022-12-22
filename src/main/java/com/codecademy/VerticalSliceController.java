package com.codecademy;

import com.codecademy.controller.OrganisationOverviewController;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.OrganisationDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;

public class VerticalSliceController {
    @FXML
    VBox root;

    @FXML
    public void initialize() throws IOException, SQLException {
        DBConnection dbConnection = new DBConnection();
        OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Organisation-Overview-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationOverviewController controller = new OrganisationOverviewController();
            controller.setOrganisationDAO(organisationDAO);
            controller.setRoot(root);
            return controller;
        });

        Pane newPane = loader.load();
        root.getChildren().add(newPane);
    }
}
