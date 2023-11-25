package controllers;

import Application.MySQL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Bookmark implements Initializable {
    @FXML
    private TextField textSearch;
    @FXML
    private ListView<String> listBookmark;
    @FXML
    private WebView webView;
    @FXML
    private Button deleteButton;

    private MySQL mySQL = MySQL.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webView.getEngine().loadContent(mySQL.htmlSelectFromBookmark(""));
        searchBookmark();

        controllerSearch();
        controllerSelect();
    }

    public void controllerSearch() {
        textSearch.addEventFilter(KeyEvent.ANY, e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                textSearch.setEditable(false);
                lookup(textSearch.getText());
            }
            else {
                if (!textSearch.isEditable()) {
                    textSearch.setEditable(true);
                }
                searchBookmark();
            }
        });

        textSearch.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!textSearch.isEditable()) {
                textSearch.setEditable(true);
            }
        });
    }

    public void searchBookmark() {
        List<String> listWord = mySQL.searchFromBookmark(textSearch.getText());
//        listBookmark.setPrefHeight(Math.min(25 * listWord.size(), 370));
        ObservableList<String> observableList = FXCollections.observableArrayList(listWord);

//        listBookmark.setFocusTraversable(false);
        listBookmark.setItems(observableList);
    }

    public void controllerSelect() {
        listBookmark.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
            if (!deleteButton.isVisible()) {
                deleteButton.setVisible(true);
            }

            String word = listBookmark.getSelectionModel().getSelectedItem();
            lookup(word);
        });

    }

    public void controllerDelete() {
        deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            mySQL.deleteBookmark(listBookmark.getSelectionModel().getSelectedItem());

            searchBookmark();
            deleteButton.setVisible(false);
        });
    }

    private void lookup(String word) {
        String definition = mySQL.htmlSelectFromBookmark(word);
//        System.out.println(definition);

        webView.getEngine().loadContent(definition);
    }
}
