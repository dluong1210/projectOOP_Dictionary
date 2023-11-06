package controllers;

import Application.GoogleTranslateAPI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Translate implements Initializable {
    @FXML
    private Pane sceneTrans;
    @FXML
    private Pane input;
    @FXML
    private Pane output;
    @FXML
    private TextArea textInput;
    @FXML
    private TextArea textOutput;
    @FXML
    private ChoiceBox<String> langueInput;
    @FXML
    private ChoiceBox<String> langueOutput;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langueInput.getItems().addAll("Vietnamese", "English", "Japanese", "Chinese", "French");
        langueInput.getSelectionModel().select("Vietnamese");
        langueOutput.getItems().addAll("Vietnamese", "English", "Japanese", "Chinese", "French");
        langueOutput.getSelectionModel().select("English");
        checkFocus(new ActionEvent());
        translate(new ActionEvent());
    }

    public void translate(ActionEvent event) {
        textInput.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String textIn = textInput.getText();
                String textOut;
                try {
                    String src = convertLangueText(langueInput.getValue());
                    String target = convertLangueText(langueOutput.getValue());
                    textOut = GoogleTranslateAPI.translate(textIn, src, target);
                } catch (Exception exception) {
                    textOut = "Over limit character of month =((";
                }

                textOutput.setText(textOut);
                output.getStyleClass().clear();
                output.getStyleClass().add("active-pane");
            }
        });
    }

    private String convertLangueText(String word) {
        switch (word) {
            case "Vietnamese":
                return "vi";
            case "English":
                return "en";
            case "French":
                return "fr";
            case "Chinese":
                return "zh-CN";
            case "Japanese":
                return "ja";
        }
        return null;
    }

    public void checkFocus(ActionEvent event) {
        textInput.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            input.getStyleClass().clear();
            input.getStyleClass().add("active-pane");
        });
        output.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            output.getStyleClass().clear();
            output.getStyleClass().add("active-pane");
        });

        sceneTrans.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!textInput.getBoundsInLocal().contains(e.getX(), e.getY())) {
                input.getStyleClass().clear();
                input.getStyleClass().add("inactive-pane");
            }

            if (!textOutput.getBoundsInLocal().contains(e.getX(), e.getY())) {
                output.getStyleClass().clear();
                output.getStyleClass().add("inactive-pane");
            }
        });
    }
}
