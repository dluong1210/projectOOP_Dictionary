package controllers;

import Application.MySQL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/homeTab.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 893, 616);
        stage.setTitle("my dictionary!");
        stage.setScene(scene);
        stage.show();
    }
}
