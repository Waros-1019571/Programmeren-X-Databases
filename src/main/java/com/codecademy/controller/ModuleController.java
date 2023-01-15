package com.codecademy.controller;

import com.codecademy.entity.Course;
import com.codecademy.entity.Module;
import com.codecademy.entity.Webcast;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.ModuleDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ModuleController implements Controller {
    private DBConnection dbConnection;
    private ModuleDAO moduleDAO;
    private CourseDAO courseDAO;
    private Alert alert;

    @FXML
    private TableView<Module> moduleTableView;
    @FXML
    private TextField titleField;
    @FXML
    private TextField versionField;
    @FXML
    private TextField contactNameField;
    @FXML
    private TextField contactEmailField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ChoiceBox<Course> courseField;
    @FXML
    private DatePicker publicationDateField;

    @Override
    public void setDBConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @FXML
    public void initialize() {
        moduleDAO = new ModuleDAO(dbConnection);
        courseDAO = new CourseDAO(dbConnection);
        loadModule();
        loadCourseComboBox();

        moduleTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                titleField.setText(moduleTableView.getSelectionModel().getSelectedItem().getTitle());
                versionField.setText(String.valueOf(moduleTableView.getSelectionModel().getSelectedItem().getVersion()));
                contactNameField.setText(moduleTableView.getSelectionModel().getSelectedItem().getContactName());
                contactEmailField.setText(moduleTableView.getSelectionModel().getSelectedItem().getContactEmail());
                publicationDateField.setValue(moduleTableView.getSelectionModel().getSelectedItem().getPublicationDate());
                courseField.setValue(moduleTableView.getSelectionModel().getSelectedItem().getCourse());
                descriptionField.setText(moduleTableView.getSelectionModel().getSelectedItem().getDescription());
            }
        });
    }

    @FXML
    private void processCreateBTN() {
        Module module = new Module();
        if (!updateModuleWithInputs(module)) {
            return;
        }
        if (!moduleDAO.create(module)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal Error module couldn't be created!");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation succeeded!");
        alert.setContentText("Module has been created!");
        alert.show();

        clearFields();
        moduleTableView.getSelectionModel().clearSelection();
        loadModule();
    }

    @FXML
    private void processUpdateBTN() {
        if (isModuleNotSelected()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update confirmation");
        alert.setContentText("Are you sure you want to update this?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            throw new RuntimeException();
        }

        if (result.get() == ButtonType.OK){
            Module module = moduleTableView.getSelectionModel().getSelectedItem();
            if (!updateModuleWithInputs(module)) {
                return;
            }

            if (!moduleDAO.update(module)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Update Error");
                alert.setContentText("Webcast couldn't be updated!");
                alert.show();
                loadModule();
                return;
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update succeeded");
            alert.setContentText("Webcast has been updated!");
            alert.show();
            loadModule();
        }
    }

    @FXML
    private void processDeleteBTN() {
        if (isModuleNotSelected()) {
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
            if (!moduleDAO.delete(moduleTableView.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Module couldn't be deleted!");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion succeeded");
            alert.setContentText("Module has been deleted!");
            alert.show();
            moduleTableView.getItems().remove(moduleTableView.getSelectionModel().getSelectedIndex());
        }
    }

    public void loadModule () {
        List<Module> list = moduleDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Module list is empty");
            return;
        }

        moduleTableView.getItems().clear();
        moduleTableView.getColumns().clear();

        clearFields();

        TableColumn<Module, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        moduleTableView.getColumns().add(idCol);

        TableColumn<Module, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        moduleTableView.getColumns().add(titleCol);

        TableColumn<Module, String> publicationDateCol = new TableColumn<>("Publication date");
        publicationDateCol.setCellValueFactory(cellData -> {
            Module module = cellData.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new ReadOnlyStringWrapper(module.getPublicationDate().format(formatter));
        });
        moduleTableView.getColumns().add(publicationDateCol);

        TableColumn<Module, String> versionCol = new TableColumn<>("Version");
        versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        moduleTableView.getColumns().add(versionCol);

        TableColumn<Module, String> contactNameCol = new TableColumn<>("Contact name");
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        moduleTableView.getColumns().add(contactNameCol);

        TableColumn<Module, String> contactEmailCol = new TableColumn<>("Contact Email");
        contactEmailCol.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
        moduleTableView.getColumns().add(contactEmailCol);

        ObservableList<Module> data = FXCollections.observableArrayList(list);
        moduleTableView.setItems(data);
    }

    @FXML
    public void clearFields() {
        titleField.setText(null);
        versionField.setText(null);
        contactNameField.setText(null);
        contactEmailField.setText(null);
        publicationDateField.setValue(null);
        courseField.setValue(null);
        descriptionField.setText(null);
    }

    @FXML
    public void deSelectItemAndClearFields() {
        clearFields();
        moduleTableView.getSelectionModel().clearSelection();
    }

    private void loadCourseComboBox() {
        List<Course> list = courseDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Course list is empty for comboBox");
            return;
        }

        ObservableList<Course> data = FXCollections.observableArrayList(list);
        courseField.setItems(data);
    }

    private boolean updateModuleWithInputs(Module module) {
        try {
            module.setTitle(titleField.getText());
            module.setVersion(versionField.getText());
            module.setContactName(contactNameField.getText());
            module.setContactEmail(contactEmailField.getText());
            module.setPublicationDate(publicationDateField.getValue());
            module.setCourse(courseField.getValue());
            module.setDescription(descriptionField.getText());
            return true;
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid module input!");
            alert.setContentText("Please make sure the input is correct: " + e.getMessage());
            alert.show();
            return false;
        }
    }
    private boolean isModuleNotSelected() {
        if (moduleTableView.getSelectionModel().getSelectedItem() != null) {
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setContentText("Module hasn't been selected.\nPlease select an module!");
        alert.show();
        return true;
    }
}
