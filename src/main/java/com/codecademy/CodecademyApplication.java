package com.codecademy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CodecademyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CodecademyApplication.class.getResource("/Codecademy-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Kelvin L. 2205396, Tycho B. 2199294, Waros Y. 2209602 & Daan de V. 2205132");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
