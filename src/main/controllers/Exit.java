package controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;

public class Exit extends Application {

    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("my dictionary");
        button = new Button();
        button.setText("exit");
        button.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            //alert.setTitle("do you want exit");
            alert.setHeaderText("do you want exit");
            alert.setContentText("choose your option");

            ButtonType buttonTypeYes = new ButtonType("yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("no", ButtonBar.ButtonData.NO);
            ButtonType buttonTypeCancel = new ButtonType("cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeYes)
                System.out.println("Code for yes");
            else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
                System.out.println("Code for no");
            else
                System.out.println("Code for cancel");
            String message = result.get().getText();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("information");
            alert1.setHeaderText("notification");
            alert1.setContentText(message);
            alert1.show();
        });
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
