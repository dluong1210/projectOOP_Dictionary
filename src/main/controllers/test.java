package controllers;

import Application.MySQL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Thread databaseThread = new Thread(() -> {
            MySQL.getInstance(); // Đảm bảo bạn đã có phương thức kết nối cơ sở dữ liệu
        });
        databaseThread.start();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/searchWord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 877, 616);
        stage.setTitle("my dictionary!");
        stage.setScene(scene);
        stage.show();
    }
}
