package com.codecademy.controller;

import com.codecademy.CodecademyApplication;
import com.codecademy.entity.Course;
import com.codecademy.entity.Organisation;
import com.codecademy.entity.VoiceActor;
import com.codecademy.entity.Webcast;
import com.codecademy.logic.DBConnection;
import com.codecademy.model.CourseDAO;
import com.codecademy.model.OrganisationDAO;
import com.codecademy.model.VoiceActorDAO;
import com.codecademy.model.WebcastDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CodecademyController {
    private DBConnection dbConnection = new DBConnection();
    private OrganisationDAO organisationDAO = new OrganisationDAO(dbConnection);
    private CourseDAO courseDAO = new CourseDAO(dbConnection);
    private VoiceActorDAO voiceActorDAO = new VoiceActorDAO(dbConnection);
    private WebcastDAO webcastDAO = new WebcastDAO(dbConnection);

    @FXML
    private GridPane organisationPane;
    @FXML
    private Tab organisationTab;
    @FXML
    private GridPane voiceActorPane;
    @FXML
    private Tab voiceActorTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<Webcast> webcastTableView;
    @FXML
    private Button webcastDeleteBTN;

    @FXML
    private TableView<Course> webcastCoursesView;
    @FXML
    private TableView<VoiceActor> webcastVoiceActorsView;
    @FXML
    private TextField webcastTitleField;
    @FXML
    private TextField webcastDescriptionField;
    @FXML
    private TextField webcastURLField;
    @FXML
    private TextField webcastPublicationDateField;
    @FXML
    private TextField webcastDurationField;
    @FXML
    private Button addWebcastBTN;

    @FXML
    public void initialize() {

        addWebcastBTN.setOnAction(event -> {
            try {
                processAddWebcastBtn();
            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        webcastDeleteBTN.setOnAction(event -> {
            try {
                processDeleteWebcastBtn();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        loadOrganisation();
        loadWebcasts();
        // reload panes when selected
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(organisationTab)) {
                loadOrganisation();
            }
            if (newValue.equals(voiceActorTab)) {
                loadVoiceActor();
            }
        });
    }

    private void loadOrganisation() {
        FXMLLoader loader = new FXMLLoader(CodecademyApplication.class.getResource("Organisation-Overview-view.fxml"));
        loader.setControllerFactory(newController -> {
            OrganisationOverviewController controller = new OrganisationOverviewController();
            controller.setOrganisationDAO(organisationDAO);
            controller.setRoot(organisationPane);
            return controller;
        });

        try {
            organisationPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVoiceActor() {
        FXMLLoader loader = new FXMLLoader(CodecademyApplication.class.getResource("voiceActor-view.fxml"));
        loader.setControllerFactory(newController -> {
            VoiceActorController controller = new VoiceActorController();
            controller.setVoiceActorDAO(voiceActorDAO);
            controller.setOrganisationDAO(organisationDAO);
            return controller;
        });

        try {
            voiceActorPane.getChildren().clear();
            voiceActorPane.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadVoiceActorsForWebcast() {
        List<VoiceActor> list = voiceActorDAO.getAll();
        webcastVoiceActorsView.getItems().clear();
        webcastVoiceActorsView.getColumns().clear();

        TableColumn<VoiceActor, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        webcastVoiceActorsView.getColumns().add(idCol);

        TableColumn<VoiceActor, Object> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        webcastVoiceActorsView.getColumns().add(nameCol);

        if (list == null || list.size() == 0) {
            System.out.println("Voice actor list is empty");
            return;
        }

        ObservableList<VoiceActor> data = FXCollections.observableArrayList(list);
        webcastVoiceActorsView.setItems(data);
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

        TableColumn<Webcast, Date> publicationDateCol = new TableColumn<>("Publication date");
        urlCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        webcastTableView.getColumns().add(publicationDateCol);

        TableColumn<Webcast, Integer> durationCol = new TableColumn<>("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        webcastTableView.getColumns().add(durationCol);

        if (list == null || list.size() == 0) {
            System.out.println("Webcast list is empty");
            return;
        }

        ObservableList<Webcast> data = FXCollections.observableArrayList(list);
        webcastTableView.setItems(data);
    }
    private Date getWebcastDate() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(webcastPublicationDateField.getText());
    }

    private void processAddWebcastBtn() throws SQLException, ParseException {
        // TODO: Get courses inside the table view and bind their ID like we do with voice actors
        VoiceActor voiceActor = webcastVoiceActorsView.getSelectionModel().getSelectedItem();
        if (webcastVoiceActorsView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing voice actor");
            alert.setHeaderText("Missing voice actor");
            alert.setContentText("Please select a voice actor to attach to the webcast");
            alert.showAndWait();
        } else {
            Webcast webcast = new Webcast();
            webcast.setCourse(new Course(1, null, null, null, null)); // TODO: Course from table view
            webcast.setVoiceActor(voiceActor);
            webcast.setTitle(webcastTitleField.getText());
            webcast.setDescription(webcastDescriptionField.getText());
            webcast.setUrl(webcastURLField.getText());
            webcast.setPublicationDate(getWebcastDate());
            webcast.setDuration(Integer.parseInt(webcastDurationField.getText()));
            webcastDAO.create(webcast);
            loadWebcasts();
        }
    }

    private void processDeleteWebcastBtn() throws SQLException {
        Webcast webcast = webcastTableView.getSelectionModel().getSelectedItem();
        if (webcast == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing webcast");
            alert.setHeaderText("Missing webcast");
            alert.setContentText("Please select a webcast to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete webcast");
        alert.setHeaderText("Delete webcast " + webcast.getTitle());
        alert.setContentText("Are you sure you want to do this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            webcastDAO.delete(webcast);
            loadWebcasts();
        }
    }
}
