package com.codecademy.controller;

import com.codecademy.entity.*;
import com.codecademy.logic.Controller;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CertificateDAO;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.EnrollmentDAO;
import com.codecademy.model.StudentDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class EnrollmentController implements Controller {
    private DBConnection dbConnection;
    private EnrollmentDAO enrollmentDAO;
    private CourseDAO courseDAO;
    private StudentDAO studentDAO;
    private CertificateDAO certificateDAO;
    private List<Enrollment> enrollments;

    @FXML
    private TableView<Enrollment> enrollmentTableView;
    @FXML
    private ChoiceBox<Student> studentChoiceBox;
    @FXML
    private ChoiceBox<Course> courseChoiceBox;
    @FXML
    private TextField gradeField;
    @FXML
    private TextField signedByField;

    @Override
    public void setDBConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @FXML
    public void initialize() {
        enrollmentDAO = new EnrollmentDAO(dbConnection);
        courseDAO = new CourseDAO(dbConnection);
        studentDAO = new StudentDAO(dbConnection);
        certificateDAO = new CertificateDAO(dbConnection);

        loadEnrollments();
        loadCourseChoiceBox();
        loadStudentChoiceBox();

        enrollmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                emptyFields();
                studentChoiceBox.setValue(enrollmentTableView.getSelectionModel().getSelectedItem().getStudent());
                courseChoiceBox.setValue(enrollmentTableView.getSelectionModel().getSelectedItem().getCourse());
                if (enrollmentTableView.getSelectionModel().getSelectedItem().getCertificate() == null) {
                    return;
                }

                double grade = enrollmentTableView.getSelectionModel().getSelectedItem().getCertificate().getGrade();
                gradeField.setText(String.valueOf(grade));
                String signedBy = enrollmentTableView.getSelectionModel().getSelectedItem().getCertificate().getSignedBy();
                signedByField.setText(signedBy);
            }
        });
    }

    @FXML
    private void processEnrollStudentBTN() {
        if (studentChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error!");
            alert.setContentText("Please select a student!");
            return;
        }

        if (enrollmentExist()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error!");
            alert.setContentText("Enrollment already exists!");
            alert.show();
            return;
        }

        Enrollment enrollment = new Enrollment();
        try {
            enrollment.setStudent(studentChoiceBox.getValue());
            enrollment.setCourse(courseChoiceBox.getValue());
        } catch (Exception e ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid enrollment input");
            alert.setContentText("Please make sure the input is correct: " + e.getMessage());
            alert.show();
            return;
        }

        enrollment.setEnrollmentDate(LocalDate.now());
        if (!enrollmentDAO.create(enrollment)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal error! Student couldn't be enrolled!");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Enrollment succeeded!");
        alert.setContentText("Student has been enrolled!");
        alert.show();

        emptyFields();
        loadEnrollments();
    }

    @FXML
    private void processCertificationBTN() {
        if (!isEnrollmentSelected()) {
            return;
        }

        Enrollment enrollment = enrollmentTableView.getSelectionModel().getSelectedItem();
        if (enrollment.getCertificate() != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Error!");
            alert.setContentText("User error! Student has already been certified!");
            alert.show();
            return;
        }
        Certificate certificate = new Certificate();
        enrollment.setCertificate(certificate);
        if (!putFieldsIntoCertification(certificate)) {
            return;
        }

        enrollment.getCertificate().setCertificationDate(LocalDate.now());
        if (!enrollmentDAO.addCertification(enrollment)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal error! Student couldn't be certified!");
            alert.show();
            loadEnrollments();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Certification succeeded!");
        alert.setContentText("Student has been certified!");
        alert.show();

        emptyFields();
        loadEnrollments();
    }

    @FXML
    private void processUpdateCertificationBTN() {
        if (!isEnrollmentSelected()) {
            return;
        }

        Enrollment enrollment = enrollmentTableView.getSelectionModel().getSelectedItem();
        if (enrollment.getCertificate() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("User Error!");
            alert.setContentText("User error! Certificate needs to be created first!");
            alert.show();
            return;
        }
        if (!putFieldsIntoCertification(enrollment.getCertificate())) {
            return;
        }

        if (!certificateDAO.update(enrollment.getCertificate())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Internal Error!");
            alert.setContentText("Internal error! Certificate couldn't be changed!");
            alert.show();
            loadEnrollments();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Certificate changed!");
        alert.setContentText("Certificate has been changed!");
        alert.show();

        emptyFields();
        loadEnrollments();
    }

    @FXML
    private void deSelectItem() {
        emptyFields();
        enrollmentTableView.getSelectionModel().clearSelection();
    }

    private boolean enrollmentExist() {
        boolean enrollmentExist = false;
        for(Enrollment enrollment: this.enrollments) {
            if (enrollment.getStudent().getEmail().equals(studentChoiceBox.getValue().getEmail()) &&
                    enrollment.getCourse().getTitle().equals(courseChoiceBox.getValue().getTitle())) {
                enrollmentExist = true;
                break;
            }
        }
        return enrollmentExist;
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

    private void loadStudentChoiceBox() {
        List<Student> list = studentDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Student list is empty for comboBox");
            return;
        }

        ObservableList<Student> data = FXCollections.observableArrayList(list);
        studentChoiceBox.setItems(data);
    }


    private boolean isEnrollmentSelected() {
        Enrollment enrollment = enrollmentTableView.getSelectionModel().getSelectedItem();
        if (enrollment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error!");
            alert.setContentText("Please select an enrollment!");
            return false;
        }
        return true;
    }

    private boolean putFieldsIntoCertification(Certificate certificate) {
        try {
            certificate.setGrade(gradeField.getText());
            certificate.setSignedBy(signedByField.getText());
            return true;
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid certification input");
            alert.setContentText("Please make sure the input is correct: " + e.getMessage());
            alert.show();
            return false;
        }
    }

    private void emptyFields() {
        studentChoiceBox.setValue(null);
        courseChoiceBox.setValue(null);
        gradeField.setText(null);
        signedByField.setText(null);
    }

    private void loadEnrollments() {
        enrollments = enrollmentDAO.getAll();
        if (enrollments == null || enrollments.size() == 0) {
            System.out.println("Enrollment list is empty");
            return;
        }

        enrollmentTableView.getItems().clear();
        enrollmentTableView.getColumns().clear();
        enrollmentTableView.getSelectionModel().clearSelection();
        emptyFields();

        TableColumn<Enrollment, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(cellData -> {
            String course = cellData.getValue().getCourse().toString();
            return new ReadOnlyStringWrapper(course);
        });
        enrollmentTableView.getColumns().add(courseCol);

        TableColumn<Enrollment, String> studentCol = new TableColumn<>("Student");
        studentCol.setCellValueFactory(cellData -> {
            String course = cellData.getValue().getStudent().toString();
            return new ReadOnlyStringWrapper(course);
        });
        enrollmentTableView.getColumns().add(studentCol);

        TableColumn<Enrollment, String> enrollmentDateCol = new TableColumn<>("Enrollment date");
        enrollmentDateCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        enrollmentTableView.getColumns().add(enrollmentDateCol);

        TableColumn<Enrollment, String> certificateGradeCol = new TableColumn<>("Certificate grade");
        certificateGradeCol.setCellValueFactory(cellData -> {
            if (cellData.getValue().getCertificate() == null)  {
                return new ReadOnlyStringWrapper("");
            }
            double certificateGrade = cellData.getValue().getCertificate().getGrade();
            return new ReadOnlyStringWrapper(String.valueOf(certificateGrade));
        });
        enrollmentTableView.getColumns().add(certificateGradeCol);

        TableColumn<Enrollment, String> certificationDateCol = new TableColumn<>("Certification date");
        certificationDateCol.setCellValueFactory(cellData -> {
            Certificate certificate = cellData.getValue().getCertificate();
            if (certificate == null)  {
                return new ReadOnlyStringWrapper("");
            }
            LocalDate certificationDate = certificate.getCertificationDate().toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new ReadOnlyStringWrapper(certificationDate.format(formatter));
        });
        enrollmentTableView.getColumns().add(certificationDateCol);

        TableColumn<Enrollment, String> signedByCol = new TableColumn<>("Signed by");
        signedByCol.setCellValueFactory(cellData -> {
            Certificate certificate = cellData.getValue().getCertificate();
            if (certificate == null)  {
                return new ReadOnlyStringWrapper("");
            }
            return new ReadOnlyStringWrapper(certificate.getSignedBy());
        });
        enrollmentTableView.getColumns().add(signedByCol);

        ObservableList<Enrollment> data = FXCollections.observableArrayList(enrollments);
        enrollmentTableView.setItems(data);
    }
}
