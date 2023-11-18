package controllers;

import Application.MySQL;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Optional;

public class SearchWord implements Initializable {
    @FXML
    private Button homeButton;
    @FXML
    private Button translateButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private BorderPane scene;
    @FXML
    private TextField textSearch;
    @FXML
    private ListView<String> listFound;
    @FXML
    private BorderPane result;
    @FXML
    private WebView webView;
    @FXML
    private Button markButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private BorderPane editor;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private Button changeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnAction(e -> tabPane.getSelectionModel().select(0));
        webView.getEngine().loadContent(MySQL.htmlSelectFromDB(""));
        check(new ActionEvent());

        controllerSearch(new ActionEvent());
        selectFromList(new ActionEvent());

        controllerMark(new ActionEvent());
        controllerDelete(new ActionEvent());
        controllerEdit(new ActionEvent());
    }

    public void check(ActionEvent event) {
        translateButton.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Translate.fxml"));
            try {
                Parent translateTab = loader.load();
                Tab tab = new Tab("Google Translate", translateTab);
                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        });
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
                    System.out.println(wordSelected);
                    lookup(wordSelected);
                    textSearch.setText(wordSelected);
                    isSelected = false;
                } else {
                    wordSelected = wordClicked;
                }
            }
        });
    }

    public void controllerSearch(ActionEvent event) {
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
            if (e.getCode().equals(KeyCode.ENTER)) {
                textSearch.setEditable(false);
                lookup(textSearch.getText());
            }
            else {
                if (!textSearch.isEditable()) {
                    textSearch.setEditable(true);
                }

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
            }
        });
    }

    public void controllerMark(ActionEvent event) {
        markButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            System.out.println(wordSelected);

            try {
                if (!MySQL.checkBookmark(wordSelected)) MySQL.addBookmark(wordSelected);
                else MySQL.deleteBookmark(wordSelected);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    public void controllerDelete(ActionEvent event) {
        deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want delete word : \"" + wordSelected + "\"");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                try {
                    MySQL.deleteFromDB(wordSelected);
                    lookup(wordSelected);

                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
    }

    public void controllerEdit(ActionEvent event) {
        editButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            result.setVisible(false);
            editor.setVisible(true);

            htmlEditor.setHtmlText(MySQL.htmlSelectFromDB(wordSelected));
            Platform.runLater(() -> {
                Node[] nodes = htmlEditor.lookupAll(".tool-bar").toArray(new Node[0]);
                for (Node node : nodes) {
                    node.setVisible(false);
                    node.setManaged(false);
                }
                htmlEditor.setVisible(true);
            });
        });

        changeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            editor.setVisible(false);
            result.setVisible(true);

            Document doc = Jsoup.parse(htmlEditor.getHtmlText());
            String htmlTextToDB = doc.body().html();
            System.out.println(doc.body().html());
            try {
                MySQL.updateDB(wordSelected, htmlTextToDB);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    private void lookup(String word) {
        String definition = MySQL.htmlSelectFromDB(word);
//        System.out.println(definition);
        if (MySQL.selectFromDB(word) == null) {
            markButton.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
        } else {
            wordSelected = word;
            markButton.setVisible(true);
            deleteButton.setVisible(true);
            editButton.setVisible(true);
        }

        listFound.setVisible(false);
        listFound.getSelectionModel().select(-1);
        tabPane.getSelectionModel().select(0);
        result.setVisible(true);
        webView.getEngine().loadContent(definition);

    }



}