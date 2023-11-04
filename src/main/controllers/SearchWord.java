package controllers;

import Application.MySQL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchWord implements Initializable {
    @FXML
    private BorderPane scene;
    @FXML
    private TextField textSearch;
    @FXML
    private ListView<String> listFound;
    @FXML
    private WebView webView = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (webView == null) {
            webView = new WebView();
            webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/webview.css").toString());
        }
        search(new ActionEvent());
        selectFromList(new ActionEvent());
    }

    /*---Check double click---*/
    boolean isSelected = false;
    String wordSelected = null;
    /*------------------------*/

    public void selectFromList(ActionEvent event) {
        listFound.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String wordClicked = listFound.getSelectionModel().getSelectedItem();

                if (!isSelected) {
                    listFound.getSelectionModel().select(listFound.getSelectionModel().getSelectedIndex());
                    isSelected = true;
                    wordSelected = wordClicked;
                } else if (wordSelected.equals(wordClicked)) {
                    String definition = MySQL.selectFromDB(wordSelected);
//                    System.out.println(definition);

                    listFound.setVisible(false);
                    listFound.getSelectionModel().select(-1);
                    webView.getEngine().loadContent(definition);

                    isSelected = false;
                    wordSelected = null;
                } else {
                    isSelected = true;
                    wordSelected = wordClicked;
                }
            }
        });
    }

    public void search(ActionEvent event) {
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!listFound.getBoundsInLocal().contains(e.getX(), e.getY())
                && !textSearch.getBoundsInLocal().contains(e.getX(), e.getY())) {
                listFound.setVisible(false);
                textSearch.setEditable(false);
            }
            textSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    textSearch.setEditable(true);
                    if (!textSearch.getText().isEmpty()) listFound.setVisible(true);
                }
            });
        });

        textSearch.addEventFilter(KeyEvent.ANY, e -> {
            if (textSearch.getText().isEmpty()) {
                listFound.setVisible(false);
                return;
            } else {
                listFound.setVisible(true);
                List<String> listWord = MySQL.searchFromDB(textSearch.getText());
                listFound.setPrefHeight(Math.min(23 * listWord.size(), 235));
                ObservableList<String> observableList = FXCollections.observableArrayList(listWord);

                listFound.setItems(observableList);
            }
        });
    }
}
