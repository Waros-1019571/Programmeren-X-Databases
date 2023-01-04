package com.codecademy.controller;

import com.codecademy.CodecademyApplication;
import com.codecademy.entity.Organisation;
import com.codecademy.model.OrganisationDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class OrganisationOverviewController {
    @FXML
    TableView<Organisation> table;
    @FXML
    Button deleteBTN;
    OrganisationDAO organisationDAO;
    Pane root;
    FXMLLoader formLoader;
    OrganisationFormController controller;

    public OrganisationOverviewController() {
        this.formLoader = new FXMLLoader(CodecademyApplication.class.getResource("Organisation-Form-view.fxml"));
        this.controller = new OrganisationFormController();
    }

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
        this.controller.setOrganisationDAO(this.organisationDAO);
    }

    public void setRoot(Pane root) {
        this.root = root;
        controller.setRoot(this.root);
    }

    public void initialize() {
        TableColumn<Organisation, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameColumn);
        List<Organisation> list = organisationDAO.getAll();

        if (list == null || list.size() == 0) {
            System.out.println("list is empty");
            return;
        }

        ObservableList<Organisation> data = FXCollections.observableArrayList(list);
        table.setItems(data);
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
            if (!organisationDAO.delete(table.getSelectionModel().getSelectedItem())) {
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
            table.getItems().remove(table.getSelectionModel().getSelectedIndex());
        }
    }

    private void goToOrganisationForm(FXMLLoader loader) {
        try {
            root.getChildren().clear();
            root.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void processCreateBTN() {
        formLoader.setControllerFactory(newController -> {
            controller.setCreating(true);
            return controller;
        });

        goToOrganisationForm(formLoader);
    }

    @FXML
    private void processUpdateBTN() {
        if (isOrganisationNotSelected()) {
            return;
        }

        formLoader.setControllerFactory(newController -> {
            controller.setOrganisation(table.getSelectionModel().getSelectedItem());
            return controller;
        });

        goToOrganisationForm(formLoader);
    }

    private boolean isOrganisationNotSelected() {
        if (table.getSelectionModel().getSelectedItem() != null) {

            return false;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setContentText("Organisation hasn't been selected.\nPlease select an organisation!");
        alert.show();
        return true;
    }
}
