package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.model.OrganisationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrganisationOverviewController {
    @FXML
    TableView<Organisation> tableView;
    @FXML
    Button organisationCreateBTN;
    @FXML
    Button organisationDeleteBTN;
    OrganisationDAO organisationDAO;
    Pane root;

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void initialize() throws SQLException {
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
        organisationCreateBTN.setOnAction(event -> {
            processCreateButton();
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
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
            }
        }
    }

    private void processCreateButton() {
        Pane newPane = new Pane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/com.codecademy/Organisation-Create-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationCreateController controller = new OrganisationCreateController();
            controller.setRoot(root);
            controller.setOrganisationDAO(organisationDAO);
            return controller;
        });

        try {
            newPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            root.getChildren().clear();
            root.getChildren().add(newPane);
        }
    }
}
