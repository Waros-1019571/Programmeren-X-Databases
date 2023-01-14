package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.model.OrganisationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import java.util.List;
import java.util.Optional;

public class OrganisationOverviewController {
    @FXML
    TableView<Organisation> organisationTableView;
    @FXML
    TextField organisationNameField;
    OrganisationDAO organisationDAO;
    Pane root;

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    @FXML
    public void initialize() {
        loadOrganisations();

        organisationTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                organisationNameField.setText(organisationTableView.getSelectionModel().getSelectedItem().getName());
            }
        });

    }

    private void loadOrganisations() {
        List<Organisation> list = organisationDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Organisation list is empty");
            return;
        }

        organisationTableView.getItems().clear();
        organisationTableView.getColumns().clear();
        organisationTableView.getSelectionModel().clearSelection();
        organisationNameField.setText("");

        TableColumn<Organisation, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("organisationId"));
        organisationTableView.getColumns().add(idCol);

        TableColumn<Organisation, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        organisationTableView.getColumns().add(nameCol);

        ObservableList<Organisation> data = FXCollections.observableArrayList(list);
        organisationTableView.setItems(data);
    }
    @FXML
    private void processCreateBTN() {
        Organisation organisation = new Organisation();
        if (!updateOrganisationWithInputs(organisation)) {
            return;
        }
        if (!organisationDAO.create(organisation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Creation Error");
            alert.setContentText("Organisation couldn't be created!");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation succeeded");
        alert.setContentText("Organisation has been created!");
        alert.show();

        loadOrganisations();
    }

    @FXML
    private void processUpdateBTN() {
        if (isOrganisationNotSelected()) {
            return;
        }

        Organisation organisation = organisationTableView.getSelectionModel().getSelectedItem();
        if (!updateOrganisationWithInputs(organisation)) {
            return;
        }

        if (!organisationDAO.update(organisation)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setContentText("Organisation couldn't be Updated!");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update succeeded");
        alert.setContentText("Organisation has been Updated!");
        alert.show();

        loadOrganisations();
    }
    @FXML
    private void processDeleteBTN() {
        if (isOrganisationNotSelected()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setContentText("Are you sure you want to delete this?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            throw new RuntimeException();
        }

        if (result.get() == ButtonType.OK){
            if (!organisationDAO.delete(organisationTableView.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Organisation couldn't be deleted!");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion succeeded");
            alert.setContentText("Organisation has been deleted!");
            alert.show();
            organisationTableView.getItems().remove(organisationTableView.getSelectionModel().getSelectedIndex());
        }
    }

    private boolean updateOrganisationWithInputs(Organisation organisation) {
        try {
            organisation.setName(organisationNameField.getText());
            return true;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid organisation input");
            alert.setContentText("Please make sure the input is correct: " + e.getMessage());
            alert.show();
            return false;
        }
    }

    private boolean isOrganisationNotSelected() {
        if (organisationTableView.getSelectionModel().getSelectedItem() != null) {
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setContentText("Organisation hasn't been selected.\nPlease select an organisation!");
        alert.show();
        return true;
    }
}
