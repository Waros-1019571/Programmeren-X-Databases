package com.codecademy.controller;

import com.codecademy.entity.*;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.ModuleProgressDAO;
import com.codecademy.model.StudentDAO;
import com.codecademy.model.WebcastProgressionDAO;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class StudentProgressionController implements Controller {
    private DBConnection dbConnection;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private WebcastProgressionDAO webcastProgressionDAO;
    private ModuleProgressDAO moduleProgressionDAO;
    private List<ModuleProgress> list;
    private StringBinding progressionbound;

    @FXML
    private ChoiceBox<Student> studentChoiceBox;
    @FXML
    private ChoiceBox<Course> courseChoiceBox;
    @FXML
    private Slider webcastSlider;
    @FXML
    private TextField webcastField;
    @FXML
    private Slider courseSlider;
    @FXML
    private TextField courseField;
    @FXML
    private TableView<ModuleProgress> moduleProgressionTableView;

    @Override
    public void setDBConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @FXML
    public void initialize() {
        moduleProgressionDAO = new ModuleProgressDAO(dbConnection);
        courseDAO = new CourseDAO(dbConnection);
        studentDAO = new StudentDAO(dbConnection);
        webcastProgressionDAO = new WebcastProgressionDAO(dbConnection);

        loadStudentChoiceBox();
        loadCourseChoiceBox();

        studentChoiceBox.setOnAction(event -> {
            if (studentChoiceBox.getValue() == null) {
                return;
            }
            if (courseChoiceBox.getValue() == null) {
                return;
            }
            loadModuleProgression(studentChoiceBox.getValue(), courseChoiceBox.getValue());;
            loadWebcastStatistics();
        });
        courseChoiceBox.setOnAction(event -> {
            if (studentChoiceBox.getValue() == null) {
                return;
            }
            if (courseChoiceBox.getValue() == null) {
                return;
            }
            loadModuleProgression(studentChoiceBox.getValue(), courseChoiceBox.getValue());
            loadWebcastStatistics();
        });
        webcastSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            webcastField.setText(String.valueOf(df.format(newValue.doubleValue())));
        });

    }

    private void loadStudentChoiceBox() {
        List<Student> list = studentDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Course list is empty for comboBox");
            return;
        }

        ObservableList<Student> data = FXCollections.observableArrayList(list);
        studentChoiceBox.setItems(data);
    }

    private void loadCourseChoiceBox() {
        List<Course> list = courseDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Course list is empty for comboBox");
            return;
        }

        ObservableList<Course> data = FXCollections.observableArrayList(list);
        courseChoiceBox.setItems(data);
    }

    private void loadModuleProgression(Student student, Course course) {
        list = moduleProgressionDAO.getAll(student, course);
        moduleProgressionTableView.getItems().clear();
        moduleProgressionTableView.getColumns().clear();

        TableColumn<ModuleProgress, String> moduleCol = new TableColumn<>("Module");
        moduleCol.setCellValueFactory(cellData -> {
            ModuleProgress moduleProgress = cellData.getValue();
            return new ReadOnlyStringWrapper(moduleProgress.getModule().getTitle());
        });
        moduleProgressionTableView.getColumns().add(moduleCol);

        TableColumn<ModuleProgress, String> progressCol = new TableColumn<>("Progress");
        progressCol.setCellValueFactory(cellData -> {
            ModuleProgress moduleProgress = cellData.getValue();
            String result = moduleProgress.getProgress() == 0 ? "Not completed" : "Completed";
            return new ReadOnlyStringWrapper(result);
        });
        moduleProgressionTableView.getColumns().add(progressCol);


        ObservableList<ModuleProgress> data = FXCollections.observableArrayList(list);
        moduleProgressionTableView.setItems(data);

        updateCourseStatisticsFields();
    }
    @FXML
    public void processSaveWebcastProgress() {
        if (areChoiceBoxesEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error empty input!");
            alert.setContentText("Student or Course is empty?");
            alert.show();
            return;
        }

        WebcastProgression progression = new WebcastProgression();
        try {
            progression.setStudent(studentChoiceBox.getValue());
            progression.setProgress(webcastSlider.getValue());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error input!");
            alert.setContentText("input Error?");
            alert.show();
            loadWebcastStatistics();
            return;
        }

        if (!webcastProgressionDAO.saveProgression(progression, courseChoiceBox.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Internal Error!");
            alert.setContentText("Progression couldn't be saved?");
            alert.show();
        }
        loadWebcastStatistics();
    }

    @FXML
    public void processCompleteBTN() {
        ModuleProgress moduleProgress = new ModuleProgress();
        try {
            moduleProgress.setProgress((byte) 1);
            moduleProgress.setModule(moduleProgressionTableView.getSelectionModel().getSelectedItem().getModule());
            moduleProgress.setStudent(moduleProgressionTableView.getSelectionModel().getSelectedItem().getStudent());
            if (!moduleProgressionDAO.create(moduleProgress)) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal Error progress couldn't be added.");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creation succeeded!");
        alert.setContentText("Module has been created!");
        alert.show();

        moduleProgressionTableView.getSelectionModel().clearSelection();
        loadModuleProgression(studentChoiceBox.getValue(), courseChoiceBox.getValue());
    }

    private void updateCourseStatisticsFields() {
        double completed = 0 ;
        for (ModuleProgress progress: list) {
            if (progress.getProgress() == 1) {
                completed++;
            }
        }
        double courseStat = completed * (100 / list.size());
        courseField.setText(String.valueOf(courseStat));
        courseSlider.setValue(courseStat);
    }

    private void loadWebcastStatistics() {
        if (areChoiceBoxesEmpty()) {
            return;
        }
        double progression = webcastProgressionDAO.getProgress(studentChoiceBox.getValue(), courseChoiceBox.getValue());
        webcastField.setText(String.valueOf(progression));
    }

    private boolean areChoiceBoxesEmpty() {
        if (studentChoiceBox.getValue() == null) {
            return true;
        }
        if (courseChoiceBox.getValue() == null) {
            return true;
        }
        return false;
    }
}
