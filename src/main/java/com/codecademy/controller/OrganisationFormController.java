package com.codecademy.controller;

import com.codecademy.HelloApplication;
import com.codecademy.entity.Organisation;
import com.codecademy.model.OrganisationDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

public class OrganisationFormController {
    @FXML
    private Text title;
    @FXML
    private TextField nameField;
    @FXML
    private Button submitBTN;

    private boolean creating;
    private Organisation organisation;
    private Pane root;
    private OrganisationDAO organisationDAO;
    private String formType;

    public OrganisationFormController() {
        this.organisation = new Organisation();
        this.creating = false;
        this.formType = "Update";
    }

    public void setCreating(boolean creating) {
        this.creating = creating;
        this.formType = creating ? "Create" : "Update";
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
    }

    public void initialize() {
        title.setText(formType + " Organisation");
        submitBTN.setText(formType);

        if (!creating) {
            nameField.setText(organisation.getName());
        }
    }

    @FXML
    private void submit() {
        organisation.setName(nameField.getText());
        if (!creating) {
            if (!organisationDAO.update(organisation)) {
                System.out.println("creation error");
                organisationProcessError();
                return;
            }
            organisationConfirmation();
            return;
        }
        if (!organisationDAO.create(organisation)) {
            organisationProcessError();
            return;
        }
        organisationConfirmation();
    }

    private void organisationProcessError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Creation Error");
        alert.setContentText("Organisation couldn't be " + formType + "d!");
        alert.show();
    }

    private void organisationConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(formType + " succeeded");
        alert.setContentText("Organisation has been " + formType + "d!");
        alert.showAndWait();
        returnToOrganisationOverview();
    }

    private void returnToOrganisationOverview() {
        Pane newPane = null;
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Organisation-Overview-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationOverviewController controller = new OrganisationOverviewController();
            controller.setRoot(root);
            controller.setOrganisationDAO(organisationDAO);
            return controller;
        });

        try {
            root.getChildren().clear();
            root.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
