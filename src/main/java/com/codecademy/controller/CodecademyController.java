package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.entity.VoiceActor;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.OrganisationDAO;
import com.codecademy.model.VoiceActorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CodecademyController {
    private DBConnection dbConnection = new DBConnection();
    private OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);
    private CourseDAO courseDAO = new CourseDAO();
    private VoiceActorDAO voiceActorDAO = new VoiceActorDAO(dbConnection);

    @FXML
    private TableView<Organisation> organisationTableView;
    @FXML
    private TableView<VoiceActor> voiceActorTableView;
    @FXML
    private Button voiceActorDeleteBTN;
    @FXML
    private TableView<Organisation> organisationVoiceActorTableView;
    @FXML
    private Button createVoiceActorBTN;
    @FXML
    private TextField createVoiceActorNameField;

    @FXML
    public void initialize() throws SQLException {
        voiceActorDeleteBTN.setOnAction(event -> {
            try {
                processDeleteVoiceActorButton();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        createVoiceActorBTN.setOnAction(event -> {
            try {
                processCreateVoiceActorBtn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        loadOrganisations();
        loadVoiceActors();
    }

    private void loadOrganisations() throws SQLException {
        loadOrganisationsForTableView(organisationVoiceActorTableView);
    }

    private void loadOrganisationsForTableView(TableView<Organisation> tableView) throws SQLException {
        List<Organisation> list = organisationDAO.getAll();
        tableView.getColumns().clear();

        TableColumn<Organisation, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("organisationId"));
        tableView.getColumns().add(idCol);

        TableColumn<Organisation, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(nameCol);

        if (list == null || list.size() == 0) {
            System.out.println("Organisation list is empty");
            return;
        }

        ObservableList<Organisation> data = FXCollections.observableArrayList(list);
        tableView.setItems(data);
    }

    private void loadVoiceActors() throws SQLException {
        List<VoiceActor> list = voiceActorDAO.getAll();
        voiceActorTableView.getColumns().clear();

        TableColumn<VoiceActor, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        voiceActorTableView.getColumns().add(idCol);

        TableColumn<VoiceActor, Object> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        voiceActorTableView.getColumns().add(nameCol);

        TableColumn<VoiceActor, Integer> organisationIdCol = new TableColumn<>("Organisation ID");
        organisationIdCol.setCellValueFactory(new PropertyValueFactory<>("organisationId"));
        voiceActorTableView.getColumns().add(organisationIdCol);

        if (list == null || list.size() == 0) {
            System.out.println("Voice actor list is empty");
            return;
        }

        ObservableList<VoiceActor> data = FXCollections.observableArrayList(list);
        voiceActorTableView.setItems(data);
    }



    private void processDeleteVoiceActorButton() throws SQLException {
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
        if (result.get() == ButtonType.OK) {
            VoiceActor voiceActor = voiceActorTableView.getSelectionModel().getSelectedItem();
            voiceActorDAO.delete(voiceActor);
            loadVoiceActors();
        }
    }

    private void processCreateVoiceActorBtn() throws SQLException {
        if (organisationVoiceActorTableView.getSelectionModel().getSelectedItem() != null) {
            VoiceActor voiceActor = new VoiceActor();
            voiceActor.setName(createVoiceActorNameField.getText());
            voiceActor.setOrganisationId(organisationVoiceActorTableView.getSelectionModel().getSelectedItem().getOrganisationId());
            voiceActorDAO.create(voiceActor);
            loadVoiceActors();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing organisation");
            alert.setHeaderText("Missing organisation");
            alert.setContentText("Please select an organisation to attach to the voice actor");
            alert.showAndWait();
        }
    }
}
