package com.codecademy.controller;

import com.codecademy.entity.Course;
import com.codecademy.entity.VoiceActor;
import com.codecademy.entity.Webcast;
import com.codecademy.model.VoiceActorDAO;
import com.codecademy.model.WebcastDAO;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class WebcastController {
    @FXML
    private TableView<Webcast> webcastTableView;
    @FXML
    private ComboBox<VoiceActor> voiceActorComboBox;
    @FXML
    private ComboBox<Course> courseComboBox;
    @FXML
    private TextField webcastTitleField;
    @FXML
    private TextField webcastDescriptionField;
    @FXML
    private TextField webcastURLField;
    @FXML
    private DatePicker webcastPublicationDateField;
    @FXML
    private TextField webcastDurationField;

    VoiceActorDAO voiceActorDAO;
    WebcastDAO webcastDAO;

    public void setVoiceActorDAO(VoiceActorDAO voiceActorDAO) {
        this.voiceActorDAO = voiceActorDAO;
    }

    public void setWebcastDAO(WebcastDAO webcastDAO) {
        this.webcastDAO = webcastDAO;
    }

    @FXML
    public void initialize() {
        webcastTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                webcastTitleField.setText(webcastTableView.getSelectionModel().getSelectedItem().getTitle());
                webcastDescriptionField.setText(webcastTableView.getSelectionModel().getSelectedItem().getDescription());
                webcastURLField.setText(webcastTableView.getSelectionModel().getSelectedItem().getUrl());
                webcastPublicationDateField.setValue(webcastTableView.getSelectionModel().getSelectedItem().getPublicationDate());
                webcastDurationField.setText("" + webcastTableView.getSelectionModel().getSelectedItem().getDuration());
                voiceActorComboBox.setValue(webcastTableView.getSelectionModel().getSelectedItem().getVoiceActor());
            }
        });
        loadWebcasts();
        loadVoiceActorComboBox();
    }
    @FXML
    private void processCreateBTN() {
        if (!validateDurationField()) {
            return;
        }

        Webcast webcast = new Webcast();
        webcast.setCourse(new Course(1, null, null, null, null));
        webcast.setVoiceActor(voiceActorComboBox.getValue());
        webcast.setTitle(webcastTitleField.getText());
        webcast.setDescription(webcastDescriptionField.getText());
        webcast.setUrl(webcastURLField.getText());
        webcast.setPublicationDate(webcastPublicationDateField.getValue());
        webcast.setDuration(Integer.parseInt(webcastDurationField.getText()));

        webcastDAO.create(webcast);
        loadWebcasts();

    }

    @FXML
    private void processDeleteBTN() {
        if (isWebcastNotSelected()) {
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
            if (!webcastDAO.delete(webcastTableView.getSelectionModel().getSelectedItem())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Webcast couldn't be deleted!");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion succeeded");
            alert.setContentText("Webcast has been deleted!");
            alert.show();
            webcastTableView.getItems().remove(webcastTableView.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void processUpdateBTN() {
        if (isWebcastNotSelected()) {
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
            Webcast webcast = webcastTableView.getSelectionModel().getSelectedItem();
            webcast.setTitle(webcastTitleField.getText());
            webcast.setDescription(webcastDescriptionField.getText());
            webcast.setUrl(webcastURLField.getText());
            webcast.setPublicationDate(webcastPublicationDateField.getValue());
            webcast.setDuration(Integer.parseInt(webcastDurationField.getText()));
            webcast.setVoiceActor(voiceActorComboBox.getValue());

            if (!webcastDAO.update(webcast)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Error");
                alert.setContentText("Webcast couldn't be updated!");
                alert.show();
                return;
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion succeeded");
            alert.setContentText("Webcast has been updated!");
            alert.show();
            loadWebcasts();
        }
    }

    public boolean validateDurationField() {
        if (webcastDurationField.getText().matches("\\d+")) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input error");
        alert.setHeaderText("Input error!");
        alert.setContentText("Please put a number in the field 'Duration'!");
        alert.show();
        return false;
    }

    private void loadVoiceActorComboBox() {
        List<VoiceActor> list = voiceActorDAO.getAll();
        if (list == null || list.size() == 0) {
            System.out.println("Voice actor list is empty for comboBox");
            return;
        }

        ObservableList<VoiceActor> data = FXCollections.observableArrayList(list);
        voiceActorComboBox.setItems(data);
    }

    private void loadWebcasts() {
        List<Webcast> list = webcastDAO.getAll();
        webcastTableView.getItems().clear();
        webcastTableView.getColumns().clear();

        TableColumn<Webcast, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        webcastTableView.getColumns().add(idCol);

        TableColumn<Webcast, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        webcastTableView.getColumns().add(titleCol);

        TableColumn<Webcast, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        webcastTableView.getColumns().add(descriptionCol);

        TableColumn<Webcast, String> urlCol = new TableColumn<>("URL");
        urlCol.setCellValueFactory(new PropertyValueFactory<>("url"));
        webcastTableView.getColumns().add(urlCol);

        TableColumn<Webcast, String> publicationDateCol = new TableColumn<>("Publication date");
        publicationDateCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        publicationDateCol.setCellValueFactory(cellData -> {
            Webcast webcast = cellData.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return new ReadOnlyStringWrapper(webcast.getPublicationDate().format(formatter));
        });

        webcastTableView.getColumns().add(publicationDateCol);

        TableColumn<Webcast, Integer> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        webcastTableView.getColumns().add(durationCol);

        TableColumn<Webcast, String> voiceActorCol = new TableColumn<>("Voice actor");
        voiceActorCol.setCellValueFactory(new PropertyValueFactory<>("voiceActor"));
        webcastTableView.getColumns().add(voiceActorCol);

        TableColumn<Webcast, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        webcastTableView.getColumns().add(courseCol);

        if (list == null || list.size() == 0) {
            System.out.println("Webcast list is empty");
            return;
        }

        ObservableList<Webcast> data = FXCollections.observableArrayList(list);
        webcastTableView.setItems(data);
    }

    private boolean isWebcastNotSelected() {
        if (webcastTableView.getSelectionModel().getSelectedItem() != null) {
            return false;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setContentText("Webcast hasn't been selected.\nPlease select an webcast!");
        alert.show();
        return true;
    }
}
