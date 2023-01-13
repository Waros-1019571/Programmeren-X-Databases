package com.codecademy.controller;

import com.codecademy.entity.Student;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.StudentDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StudentController {
    private DBConnection dbConnection;
    private StudentDAO studentDAO;
    private Alert alert;
    private final ToggleGroup group;

    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public StudentController() {
        group = new ToggleGroup();
    }

    @FXML
    public TableView<Student> studentTableView;

    @FXML
    public TextField nameField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker birthdateField;
    @FXML
    public RadioButton genderFemaleRadioBTN;
    @FXML
    public RadioButton genderMaleRadioBTN;
    @FXML
    public RadioButton genderOtherRadioBTN;
    @FXML
    public TextField addressField;
    @FXML
    public TextField postalCodeField;
    @FXML
    public TextField cityField;
    @FXML
    public TextField countryField;

    @FXML
    public void initialize() {
        studentDAO = new StudentDAO(dbConnection);
        loadStudents();
        loadGenderRadioButtons();

        studentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                return;
            }
            Student student = studentTableView.getSelectionModel().getSelectedItem();
            nameField.setText(student.getName());
            emailField.setText(student.getEmail());
            birthdateField.setValue(student.getBirthDate());
            addressField.setText(student.getAddress());
            postalCodeField.setText(student.getPostalCode());
            cityField.setText(student.getCity());
            countryField.setText(student.getCountry());

            if (student.getGender() == 0) {
                genderOtherRadioBTN.setSelected(true);
            }
            if (student.getGender() == 1) {
                genderFemaleRadioBTN.setSelected(true);
            }
            if (student.getGender() == 2) {
                genderMaleRadioBTN.setSelected(true);
            }
        });
    }

    @FXML
    private void processCreateBTN() {
        Student student = new Student();
        if (!updateStudentWithInputs(student)) {
            return;
        }

        if (!studentDAO.create(student)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal Error student couldn't be created!");
            alert.show();
            return;
        }
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation succeeded!");
        alert.setContentText("Student has been created!");
        alert.show();

        emptyFields();
        loadStudents();
    }

    @FXML
    private void processUpdateBTN() {
        if (isStudentNotSelected()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Update confirmation");
        alert.setContentText("Are you sure you want to update this?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            throw new RuntimeException();
        }

        if (result.get() != ButtonType.OK) {
            return;
        }

        Student student = studentTableView.getSelectionModel().getSelectedItem();
        if (!updateStudentWithInputs(student)) {
            return;
        }

        if (!studentDAO.update(student)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setContentText("Student couldn't be updated!");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update succeeded");
        alert.setContentText("Student has been updated!");
        alert.show();

        emptyFields();
        loadStudents();
    }

    @FXML
    private void processDeleteBTN() {
        if (isStudentNotSelected()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setContentText("Are you sure you want to delete this?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isEmpty()) {
            throw new RuntimeException();
        }

        if (result.get() != ButtonType.OK){
            return;
        }

        if (!studentDAO.delete(studentTableView.getSelectionModel().getSelectedItem())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Student couldn't be deleted!");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deletion succeeded");
        alert.setContentText("Student has been deleted!");
        alert.show();
        studentTableView.getItems().remove(studentTableView.getSelectionModel().getSelectedItem());
    }

    private boolean updateStudentWithInputs(Student student) {
        try {
            student.setName(nameField.getText());
            student.setEmail(emailField.getText());
            student.setBirthDate(birthdateField.getValue());
            Toggle toggledGender = group.getSelectedToggle();
            if (toggledGender == null) {
                throw new IllegalArgumentException("Missing gender");
            }
            student.setGender((int)toggledGender.getUserData());
            student.setAddress(addressField.getText());
            student.setPostalCode(postalCodeField.getText());
            student.setCity(cityField.getText());
            student.setCountry(countryField.getText());
            return true;
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid student input");
            alert.setContentText("Please make sure the input is correct: " + e.getMessage());
            alert.show();
            return false;
        }
    }

    private void emptyFields() {
        nameField.setText("");
        emailField.setText("");
        birthdateField.setValue(null);
        group.selectToggle(null);
        addressField.setText("");
        postalCodeField.setText("");
        cityField.setText("");
        countryField.setText("");
    }

    private boolean isStudentNotSelected() {
        if (studentTableView.getSelectionModel().getSelectedItem() != null) {
            return false;
        }

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setContentText("Student hasn't been selected.\nPlease select an webcast!");
        alert.show();
        return true;
    }

    private void loadGenderRadioButtons(){
        genderFemaleRadioBTN.setToggleGroup(group);
        genderFemaleRadioBTN.setUserData(1);
        genderMaleRadioBTN.setToggleGroup(group);
        genderMaleRadioBTN.setUserData(2);
        genderOtherRadioBTN.setToggleGroup(group);
        genderOtherRadioBTN.setUserData(0);
    }

    public void loadStudents() {
        List<Student> list = studentDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Student list is empty");
            return;
        }

        studentTableView.getItems().clear();
        studentTableView.getColumns().clear();
        studentTableView.getSelectionModel().clearSelection();
        emptyFields();

        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        studentTableView.getColumns().add(idCol);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentTableView.getColumns().add(nameCol);

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentTableView.getColumns().add(emailCol);

        TableColumn<Student, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().genderToString()));
        studentTableView.getColumns().add(genderCol);

        TableColumn<Student, String> streetCol = new TableColumn<>("Address");
        streetCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        studentTableView.getColumns().add(streetCol);

        TableColumn<Student, String> postalCodeCol = new TableColumn<>("Postal code");
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        studentTableView.getColumns().add(postalCodeCol);

        TableColumn<Student, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        studentTableView.getColumns().add(cityCol);

        TableColumn<Student, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        studentTableView.getColumns().add(countryCol);

        ObservableList<Student> data = FXCollections.observableArrayList(list);
        studentTableView.setItems(data);
    }
}
