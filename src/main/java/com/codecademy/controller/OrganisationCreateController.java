package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.model.OrganisationDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.nio.Buffer;

public class OrganisationCreateController {
    @FXML
    private Button createBTN;
    @FXML
    private TextField nameField;
    private Pane root;
    private OrganisationDAO organisationDAO;
    public void setRoot(Pane root) {
        this.root = root;
    }

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
    }

    public void initialize() {
        createBTN.setOnAction(event -> {
            proccessCreateButton();
        });
    }

    public void proccessCreateButton() {
        Organisation organisation = new Organisation();
        organisation.setName(nameField.getText());

        organisationDAO.create(organisation);
        returnToOrganisationOverview();

    }

    public void returnToOrganisationOverview(){
        Pane newPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Organisation-Overview-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationOverviewController controller = new OrganisationOverviewController();
            controller.setRoot(root);
            controller.setOrganisationDAO(organisationDAO);
            return controller;
        });

        try {
            newPane = loader.load();
            ;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            root.getChildren().clear();
            root.getChildren().add(newPane);
        }
    }

}
