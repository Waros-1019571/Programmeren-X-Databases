module com.codecademy.codecademy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.codecademy.codecademy to javafx.fxml;
    exports com.codecademy.codecademy;
}