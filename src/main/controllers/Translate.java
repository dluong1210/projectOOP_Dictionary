package controllers;

import Application.GoogleTranslateAPI;
import Application.VoiceRSS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
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
    @FXML
    private Button speakInput;
    @FXML
    private Button speakOutput;
    @FXML
    private Button swapButton;
    @FXML
    private Button translateButton;

    private String currentInputText;
    private String currentOutputText;

    private String convertLangueLabel(String label) {
        return switch (label) {
            case "Vietnamese" -> "vi";
            case "English" -> "en";
            case "French" -> "fr";
            case "Chinese" -> "zh-CN";
            case "Japanese" -> "ja";
            default -> null;
        };
    }

    private String convertSpeechLabel(String label) {
        return switch (label) {
            case "Vietnamese" -> "vi-vn";
            case "English" -> "en-us";
            case "French" -> "fr-fr";
            case "Chinese" -> "zh-cn";
            case "Japanese" -> "ja-jp";
            default -> null;
            };
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langueInput.getItems().addAll("Vietnamese", "English", "Japanese", "Chinese", "French");
        langueInput.getSelectionModel().select("Vietnamese");
        langueOutput.getItems().addAll("Vietnamese", "English", "Japanese", "Chinese", "French");
        langueOutput.getSelectionModel().select("English");

        checkMouse(new ActionEvent());
        controllerTranslate(new ActionEvent());
        controllerSpeak(new ActionEvent());
        controllerSwapLabel();
    }

    private void translate() {
            textInput.setEditable(false);
            input.getStyleClass().clear();
            input.getStyleClass().add("inactive-pane");

            String textIn = textInput.getText();
            String textOut = "";
            if (!textIn.isEmpty()) {
                try {
                    String src = convertLangueLabel(langueInput.getValue());
                    String target = convertLangueLabel(langueOutput.getValue());
                    textOut = GoogleTranslateAPI.translate(textIn, src, target);
                } catch (Exception exception) {
                    textOut = textIn;
                }
            }

            textOutput.setText(textOut);
            output.getStyleClass().clear();
            output.getStyleClass().add("active-pane");
    }

    private void controllerTranslate(ActionEvent event) {
        KeyCombination nextLine = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN);
        textInput.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (nextLine.match(e)) {
                    textInput.appendText("\n");
                }
                else{
                    e.consume();
                    translate();
                }
            }
        });

        translateButton.setOnAction(e -> {
            System.out.println("Translate");
            translate();
        });
    }

    private void controllerSpeak(ActionEvent event) {
        speakInput.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            speakText(textInput, langueInput.getValue(), true);
        });

        speakOutput.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            speakText(textOutput, langueOutput.getValue(), false);
        });

    }

    public void controllerSwapLabel() {
        swapButton.setOnAction(e -> {
            String labelInput = langueInput.getValue();
            String labelOutput = langueOutput.getValue();
            langueInput.getSelectionModel().select(labelOutput);
            langueOutput.getSelectionModel().select(labelInput);

//            String input = textInput.getText();
            String output = textOutput.getText();
            textInput.setText(output);
            translate();
        });
    }

    private void speakText(TextArea text, String label, boolean isInput) {
        if (text.getText().isEmpty()) return;
        String check = isInput ? currentInputText : currentOutputText;
        String labelSpeech = convertSpeechLabel(label);

        if (!text.getText().equals(check) && !text.getText().isEmpty()) {
            if (isInput) {
                VoiceRSS.setAudio(text.getText(), labelSpeech, "translateInput");
                currentInputText = text.getText();
            } else {
                VoiceRSS.setAudio(text.getText(), labelSpeech, "translateOutput");
                currentOutputText = text.getText();
            }

        }

        Media media = null;
        try {
            String path;
            if (isInput) {
                path = new File("src/resources/audio/translateInput.wav").toURI().toString();
            } else {
                path = new File("src/resources/audio/translateOutput.wav").toURI().toString();
            }

            media = new Media(path);
            System.out.println(path);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        MediaPlayer player = new MediaPlayer(media);
        player.play();

    }

    private void checkMouse(ActionEvent event) {
        textInput.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            textInput.setEditable(true);
            input.getStyleClass().clear();
            input.getStyleClass().add("active-pane");
        });
//        output.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
//            output.getStyleClass().clear();
//            output.getStyleClass().add("active-pane");
//        });

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
