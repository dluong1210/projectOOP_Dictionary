package controllers;

import Application.MySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Bookmark implements Initializable {
    @FXML
    private TextField textSearch;
    @FXML
    private ListView<String> listBookmark;
    @FXML
    private BorderPane definitionPane;
    @FXML
    private WebView webView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void controllerSearch() {
        textSearch.addEventFilter(KeyEvent.ANY, e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                textSearch.setEditable(false);
                lookup(textSearch.getText());
            }
            else {
                List<String> listWord = MySQL.searchFromBookmark(textSearch.getText());
                listBookmark.setPrefHeight(Math.min(23 * listWord.size(), 370));
                ObservableList<String> observableList = FXCollections.observableArrayList(listWord);

                listBookmark.setItems(observableList);
            }
        });
    }

    private void lookup(String word) {
        String definition = "";
        try {
            definition = MySQL.htmlSelectFromBookmark(word);
            System.out.println(definition);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        webView.getEngine().loadContent(definition);
    }
}
