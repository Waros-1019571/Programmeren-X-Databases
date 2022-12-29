package com.codecademy.controller;

import com.codecademy.entity.Organisation;
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
    private TableView<Organisation> tableView;
    @FXML
    private Button organisationDeleteBTN;
    @FXML
    private Button createBTN;
    @FXML
    private TextField nameField;

    @FXML
    public void initialize() throws SQLException {
        loadOrganisations();
    }

    private void loadOrganisations() throws SQLException {
        tableView.getColumns().clear();
        TableColumn<Organisation, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        List<Organisation> list = organisationDAO.getAll();
        tableView.getColumns().add(nameCol);

        if (list == null || list.size() == 0) {
            System.out.println("list is empty");
            return;
        }

        ObservableList<Organisation> data = FXCollections.observableArrayList(list);
        tableView.setItems(data);

        organisationDeleteBTN.setOnAction(event -> {
            processDeleteButton();
        });
        createBTN.setOnAction(event -> {
            try {
                proccessCreateButton();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void processDeleteButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you sure you want to do this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Organisation organisation = tableView.getSelectionModel().getSelectedItem();
            try {
                organisationDAO.delete(organisation);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void proccessCreateButton() throws SQLException {
        Organisation organisation = new Organisation();
        organisation.setName(nameField.getText());
        organisationDAO.create(organisation);
        loadOrganisations();
    }
}
