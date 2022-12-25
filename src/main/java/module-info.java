module com.codecademy.codecademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;


    opens com.codecademy to javafx.fxml;
    exports com.codecademy;
    exports com.codecademy.controller;
    opens com.codecademy.controller to javafx.fxml;
    opens com.codecademy.entity to javafx.base;
    exports com.codecademy.entity;
}