package com.codecademy.controller;

import com.codecademy.entity.Course;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class CourseController implements Controller {
    private DBConnection dbConnection;
    private CourseDAO courseDAO;
    private ToggleGroup toggleGroup;
    private Alert alert;

    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TitledPane moduleTitledPane;
    @FXML
    private TextField titleField;
    @FXML
    private TextField topicField;
    @FXML
    private TextField ownerNameField;
    @FXML
    private TextField ownerEmailField;
    @FXML
    private RadioButton beginnerRadioBTN;
    @FXML
    private RadioButton intermediateRadioBTN;
    @FXML
    private RadioButton expertRadioBTN;
    @FXML
    private TextArea descriptionField;

    @Override
    public void setDBConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @FXML
    public void initialize() {
        this.courseDAO = new CourseDAO(dbConnection);

        moduleTitledPane.setExpanded(false);
        moduleTitledPane.setOnMouseClicked(this::toggleTitlePane);

        toggleGroup = new ToggleGroup();
        beginnerRadioBTN.setUserData(0);
        beginnerRadioBTN.setToggleGroup(toggleGroup);
        intermediateRadioBTN.setUserData(1);
        intermediateRadioBTN.setToggleGroup(toggleGroup);
        expertRadioBTN.setUserData(2);
        expertRadioBTN.setToggleGroup(toggleGroup);

        courseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                deSelectItemAndClearFields();
                return;
            }
            clearFields();
            updateFields(newSelection);
        });

        loadCourses();
    }

    @FXML
    private void processCreateBTN() {
        Course course = new Course();
        updateCourseWithFieldData(course);
        if (!courseDAO.create(course)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal Error course couldn't be created!");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation succeeded!");
        alert.setContentText("Course has been created!");
        alert.show();

        deSelectItemAndClearFields();
        loadCourses();
    }

    @FXML
    private void processUpdateBTN() {
        Course course = courseTableView.getSelectionModel().getSelectedItem();
        updateCourseWithFieldData(course);
        if (!courseDAO.update(course)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal Error course couldn't be Updated!");
            alert.show();
            loadCourses();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update succeeded!");
        alert.setContentText("Course has been updated!");
        alert.show();

        deSelectItemAndClearFields();
        loadCourses();
    }

    @FXML
    private void processDeleteBTN() {
        Course course = courseTableView.getSelectionModel().getSelectedItem();;
        if (!courseDAO.delete(course)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal Error course couldn't be deleted!");
            alert.show();
            return;
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Delete succeeded!");
        alert.setContentText("Course has been deleted!");
        alert.show();

        courseTableView.getItems().remove(courseTableView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void deSelectItemAndClearFields() {
        clearFields();
        courseTableView.getSelectionModel().clearSelection();
    }

    private void loadCourses() {
        List<Course> list = courseDAO.getAll();
        courseTableView.getItems().clear();
        courseTableView.getColumns().clear();

        TableColumn<Course, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseTableView.getColumns().add(idCol);

        TableColumn<Course, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        courseTableView.getColumns().add(titleCol);

        TableColumn<Course, String> topicCol = new TableColumn<>("Topic");
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        courseTableView.getColumns().add(topicCol);

        TableColumn<Course, String> ownerNameCol = new TableColumn<>("Owner name");
        ownerNameCol.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        courseTableView.getColumns().add(ownerNameCol);

        TableColumn<Course, String> ownerEmailCol = new TableColumn<>("Owner Email");
        ownerEmailCol.setCellValueFactory(new PropertyValueFactory<>("ownerEmail"));
        courseTableView.getColumns().add(ownerEmailCol);

        TableColumn<Course, String> courseLevelCol = new TableColumn<>("Course level");
        courseLevelCol.setCellValueFactory(cellData -> {
            Course course = cellData.getValue();
            return new ReadOnlyStringWrapper(course.getCourseLevelToString());
        });
        courseTableView.getColumns().add(courseLevelCol);

        if (list == null || list.size() == 0) {
            System.out.println("Webcast list is empty");
            return;
        }

        ObservableList<Course> data = FXCollections.observableArrayList(list);
        courseTableView.setItems(data);
    }

    private void updateCourseWithFieldData(Course course) {
        course.setTitle(titleField.getText());
        course.setTopic(topicField.getText());
        course.setOwnerName(ownerNameField.getText());
        course.setOwnerEmail(ownerEmailField.getText());
        course.setCourseLevel((int) toggleGroup.getSelectedToggle().getUserData());
        course.setDescription(descriptionField.getText());
    }

    private void updateFields(Course course) {
        titleField.setText(course.getTitle());
        topicField.setText(course.getTopic());
        ownerNameField.setText(course.getOwnerName());
        ownerEmailField.setText(course.getOwnerEmail());
        descriptionField.setText(course.getDescription());
        if (course.getCourseLevel() == 0) {
            beginnerRadioBTN.setSelected(true);
        }
        if (course.getCourseLevel() == 1) {
            intermediateRadioBTN.setSelected(true);
        }
        if (course.getCourseLevel() == 2) {
            expertRadioBTN.setSelected(true);
        }
    }

    private void toggleTitlePane(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            if (!moduleTitledPane.isExpanded()) {
                moduleTitledPane.setExpanded(true);
                return;
            }
            moduleTitledPane.setExpanded(false);
        }
    }

    private void clearFields() {
        titleField.setText("");
        topicField.setText("");
        ownerNameField.setText("");
        ownerEmailField.setText("");
        toggleGroup.selectToggle(null);
        descriptionField.setText("");
    }
}
