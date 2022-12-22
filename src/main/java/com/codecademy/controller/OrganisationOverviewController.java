package com.codecademy.controller;

import com.codecademy.entity.Organisation;
import com.codecademy.model.OrganisationDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.List;

public class OrganisationOverviewController {
    @FXML
    TableView<Organisation> tableView;
    OrganisationDAO organisationDAO;

    public void setOrganisationDAO(OrganisationDAO organisationDAO) {
        this.organisationDAO = organisationDAO;
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
    }
}
