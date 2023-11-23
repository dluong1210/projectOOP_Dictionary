package controllers;

import Application.MySQL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Login Dictionary");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
//        Login.loginPage();
    }
}
