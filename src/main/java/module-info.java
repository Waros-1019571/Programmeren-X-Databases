module com.codecademy.codecademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.codecademy to javafx.fxml;
    exports com.codecademy;
}