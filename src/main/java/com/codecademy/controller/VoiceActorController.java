package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.entity.VoiceActor;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.OrganisationDAO;
import com.codecademy.model.VoiceActorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class VoiceActorController implements Controller {
    private DBConnection dbConnection;
    private VoiceActorDAO voiceActorDAO;
    private OrganisationDAO organisationDAO;

    @FXML
    TextField voiceActorNameField;
    @FXML
    private TableView<VoiceActor> voiceActorTableView;
    @FXML
    private ComboBox<Organisation> organisationComboBox;

    @Override
    public void setDBConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @FXML
    public void initialize() {
        this.voiceActorDAO = new VoiceActorDAO(dbConnection);
        this.organisationDAO = new OrganisationDAO(dbConnection);

        // update fields to selected item from tableView
        voiceActorTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                voiceActorNameField.setText(voiceActorTableView.getSelectionModel().getSelectedItem().getName());
                List<Organisation> organisationList = organisationComboBox.getItems();

                // set organisation to fetched organisation by default
                Organisation newOrganisation = voiceActorTableView.getSelectionModel().getSelectedItem().getOrganisation();
                // set organisation to organisation from combobox so it gets highlighted
                for (Organisation organisation: organisationList) {
                    if (newOrganisation.getOrganisationId() == organisation.getOrganisationId()) {
                        newOrganisation = organisation;
                    }
                }
                organisationComboBox.setValue(newOrganisation);
            }
        });

        loadVoiceActors();
        loadComboBox();
    }

    @FXML
    private void processCreateBTN() {
        if (organisationComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing organisation");
            alert.setHeaderText("Missing organisation");
            alert.setContentText("Please select an organisation to attach to the voice actor");
            alert.show();
            return;
        }

        VoiceActor voiceActor = new VoiceActor();
        voiceActor.setName(voiceActorNameField.getText());
        voiceActor.setOrganisation(organisationComboBox.getValue());
        if (!voiceActorDAO.create(voiceActor)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Creation Error");
            alert.setContentText("Voice actor couldn't be created!");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation succeeded!");
        alert.setContentText("Voice actor has been created!");
        alert.show();

        loadVoiceActors();
        clearInputs();
    }

    @FXML
    private void processUpdateBTN() {
        Alert alert;
        if (voiceActorTableView.getSelectionModel().getSelectedItem() == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing voice actor");
            alert.setHeaderText("Missing voice actor");
            alert.setContentText("Please select a voice actor to delete");
            alert.showAndWait();
            return;
        }

        VoiceActor voiceActor = voiceActorTableView.getSelectionModel().getSelectedItem();
        voiceActor.setName(voiceActorNameField.getText());
        voiceActor.setOrganisation(organisationComboBox.getValue());
        if (!voiceActorDAO.update(voiceActor)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setContentText("Voice actor couldn't be updated!");
            alert.show();
            loadVoiceActors();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update succeeded");
        alert.setContentText("Voice actor has been updated!");
        alert.show();

        clearInputs();
        loadVoiceActors();
    }

    @FXML
    private void processDeleteBTN() {
        if (voiceActorTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing voice actor");
            alert.setHeaderText("Missing voice actor");
            alert.setContentText("Please select a voice actor to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete voice actor");
        alert.setHeaderText("Delete voice actor");
        alert.setContentText("Are you sure you want to do this?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            throw new RuntimeException();
        }

        if (result.get() == ButtonType.OK) {
            VoiceActor voiceActor = voiceActorTableView.getSelectionModel().getSelectedItem();
            if (!voiceActorDAO.delete(voiceActor)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Voice actor couldn't be deleted!");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion succeeded");
            alert.setContentText("Voice actor has been deleted!");
            alert.show();

            voiceActorTableView.getItems().remove(voiceActorTableView.getSelectionModel().getSelectedIndex());
        }
    }

    private void loadComboBox() {
        List<Organisation> list = organisationDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Organisation list is empty for comboBox");
            return;
        }

        ObservableList<Organisation> data = FXCollections.observableArrayList(list);
        organisationComboBox.setItems(data);
    }

    private void loadVoiceActors() {
        List<VoiceActor> list = voiceActorDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Voice actor list is empty");
            return;
        }

        voiceActorTableView.getItems().clear();
        voiceActorTableView.getColumns().clear();

        TableColumn<VoiceActor, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        voiceActorTableView.getColumns().add(idCol);

        TableColumn<VoiceActor, Object> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        voiceActorTableView.getColumns().add(nameCol);

        TableColumn<VoiceActor, String> organisationCol = new TableColumn<>("Organisation");
        organisationCol.setCellValueFactory(new PropertyValueFactory<>("organisation"));
        voiceActorTableView.getColumns().add(organisationCol);

        ObservableList<VoiceActor> data = FXCollections.observableArrayList(list);
        voiceActorTableView.setItems(data);
    }

    private void clearInputs() {
        voiceActorNameField.setText("");
        organisationComboBox.setValue(null);
    }
}
