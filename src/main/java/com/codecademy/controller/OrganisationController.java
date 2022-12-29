package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.OrganisationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrganisationController {
    private final DBConnection dbConnection = new DBConnection();
    private final OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);

    @FXML
    private TableView<Organisation> organisationTableView;
    @FXML
    private Button createOrganisationBTN;
    @FXML
    private Button updateOrganisationBTN;
    @FXML
    private Button deleteOrganisationBTN;
    @FXML
    private TextField organisationNameField;

    @FXML
    public void initialize() throws SQLException {
        createOrganisationBTN.setOnAction(event -> {
            try {
                processCreateOrganisationButton();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        updateOrganisationBTN.setOnAction(event -> {
            try {
                processUpdateOrganisationBtn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        deleteOrganisationBTN.setOnAction(event -> {
            try {
                processDeleteOrganisationButton();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        loadOrganisations();
    }

    private void loadOrganisations() throws SQLException {
        List<Organisation> list = organisationDAO.getAll();
        organisationTableView.getColumns().clear();

        TableColumn<Organisation, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("organisationId"));
        organisationTableView.getColumns().add(idCol);

        TableColumn<Organisation, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        organisationTableView.getColumns().add(nameCol);

        if (list == null || list.size() == 0) {
            System.out.println("Organisation list is empty");
            return;
        }

        ObservableList<Organisation> data = FXCollections.observableArrayList(list);
        organisationTableView.setItems(data);
    }

    private void processCreateOrganisationButton() throws SQLException {
        Organisation organisation = new Organisation();
        organisation.setName(organisationNameField.getText());
        organisationDAO.create(organisation);
        loadOrganisations();
    }

    private void processUpdateOrganisationBtn() throws SQLException {
        Organisation organisation = organisationTableView.getSelectionModel().getSelectedItem();
        if (organisation == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing organisation");
            alert.setHeaderText("Missing organisation");
            alert.setContentText("Please select an organisation to update");
            alert.showAndWait();
            return;
        }

        organisation.setName(organisationNameField.getText());
        organisationDAO.update(organisation);
        loadOrganisations();
    }

    private void processDeleteOrganisationButton() throws SQLException {
        Organisation organisation = organisationTableView.getSelectionModel().getSelectedItem();
        if (organisation == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No organisation selected");
            alert.setHeaderText("No organisation selected");
            alert.setContentText("Please select an organisation to delete");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete organisation");
        alert.setHeaderText("Delete organisation " + organisation.getName());
        alert.setContentText("Are you sure you want to do this?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            organisationDAO.delete(organisation);
            loadOrganisations();
        }
    }
}
