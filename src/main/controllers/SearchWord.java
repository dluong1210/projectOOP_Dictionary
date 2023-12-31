package controllers;

import application.MySQL;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
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
    private Button addWordButton;
    @FXML
    private Button bookmarkButton;
    @FXML
    private Button gameButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button exitButton;
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
    private ImageView imgMark;
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
    @FXML
    private Button cancelChangeButton;
    @FXML
    private Rectangle rectangle;
    private Button tabSelected;
    private final Image imageMarked = new Image(getClass().getResourceAsStream("/icon/marked-icon.png"));
    private final Image imageUnmarked = new Image(getClass().getResourceAsStream("/icon/notmarked-icon.png"));

    private MySQL mySQL = MySQL.getInstance();
    private boolean needReloadBookmark = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabSelected = homeButton;

        loadTab();
        selectTab();
        tabPane.setFocusTraversable(false);
        controllerSearch();

        controllerMark();
        controllerDelete();
        controllerEdit();
    }

    public void loadTab() {
        try {
            FXMLLoader loaderTranslateTab = new FXMLLoader(getClass().getResource("/views/translateTab.fxml"));
            FXMLLoader loaderAddwordTab = new FXMLLoader(getClass().getResource("/views/addWordTab.fxml"));
            FXMLLoader loaderBookmarkTab = new FXMLLoader(getClass().getResource("/views/bookmarkTab.fxml"));
            FXMLLoader loaderGameTab = new FXMLLoader(getClass().getResource("/views/gameTab.fxml"));

            Parent translateTab = loaderTranslateTab.load();
            Parent addWordTab = loaderAddwordTab.load();
            Parent bookmarkTab = loaderBookmarkTab.load();
            Parent gameTab = loaderGameTab.load();

            Tab tab2 = new Tab("Google Translate", translateTab);
            Tab tab3 = new Tab("Add new word", addWordTab);
            Tab tab4 = new Tab("Bookmark", bookmarkTab);
            Tab tab5 = new Tab("Game", gameTab);

            tabPane.getTabs().addAll(tab2, tab3, tab4, tab5);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void selectTab() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), rectangle);

        homeButton.setOnAction(e -> {
            tabSelected.setOpacity(0.5);
            tabSelected = homeButton;

            homeButton.setOpacity(1);
            transition.setByY(92.5 - rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY());
            transition.play();

            tabPane.getSelectionModel().select(0);
        });

        translateButton.setOnAction(e -> {
            tabSelected.setOpacity(0.5);
            tabSelected = translateButton;

            translateButton.setOpacity(1);
            transition.setByY(137.5 - rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY());
            transition.play();

            tabPane.getSelectionModel().select(1);
        });

        addWordButton.setOnAction(e -> {
            tabSelected.setOpacity(0.5);
            tabSelected = addWordButton;

            addWordButton.setOpacity(1);
            transition.setByY(182.5 - rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY());
            transition.play();

            tabPane.getSelectionModel().select(2);
        });

        bookmarkButton.setOnAction(e -> {
            tabSelected.setOpacity(0.5);
            tabSelected = bookmarkButton;

            bookmarkButton.setOpacity(1);
            transition.setByY(227.5 - rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY());
            transition.play();

            if (needReloadBookmark) {
                try {
                    FXMLLoader loaderBookmarkTab = new FXMLLoader(getClass().getResource("/views/bookmarkTab.fxml"));
                    Parent bookmarkTab = loaderBookmarkTab.load();
                    tabPane.getTabs().set(3, new Tab("Bookmark", bookmarkTab));

                } catch (IOException ex) {
                    System.out.println("faild bookmark tab");
                }

                needReloadBookmark = false;
            }

            tabPane.getSelectionModel().select(3);
        });

        gameButton.setOnAction(e -> {
            tabSelected.setOpacity(0.5);
            tabSelected = gameButton;

            gameButton.setOpacity(1);
            transition.setByY(272.5 - rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY());
            transition.play();

            tabPane.getSelectionModel().select(4);

//            Stage newStage = new Stage();
//
//            try {
//                loadGamePage(newStage);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            newStage.show();
        });

        logoutButton.setOnAction(e -> {
            Stage newStage = new Stage();

            try {
                loadLoginPage(newStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
            newStage.show();
        });

        exitButton.setOnAction(e -> {
            Platform.exit();
        });

    }

    /*---Check double click---*/
    boolean isSelected = false;
    String wordSelected = null;
    /*------------------------*/

    public void selectFromList() {
        listFound.setOnMouseClicked(e -> {
//                if (e.getClickCount() == 1) {
                String wordClicked = listFound.getSelectionModel().getSelectedItem();
                if (wordClicked == null) return;

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
//                }
        });
    }

    public void controllerSearch() {
        scene.setOnMouseClicked(event -> {
            Node source = (Node) event.getTarget();
            if (!source.getParent().equals(listFound)) {
                listFound.setVisible(false);
                textSearch.setEditable(false);
            }
        });

        textSearch.setOnMouseClicked(e -> {
            textSearch.setEditable(true);
            if (!textSearch.getText().isEmpty()) listFound.setVisible(true);

            if (editor.isVisible()) {
                editor.setVisible(false);
                cancelChangeButton.setVisible(false);
                changeButton.setVisible(false);
            }
        });

        textSearch.addEventFilter(KeyEvent.ANY, e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                textSearch.setEditable(false);
                if (tabSelected != homeButton) {
                    tabSelected.setOpacity(0.5);
                    tabSelected = homeButton;

                    homeButton.setOpacity(1);
                    TranslateTransition transition = new TranslateTransition(Duration.seconds(0.3), rectangle);
                    transition.setByY(92.5 - rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY());
                    transition.play();

                    tabPane.getSelectionModel().select(0);
                }
                lookup(textSearch.getText());

            } else {
                if (!textSearch.isEditable()) {
                    textSearch.setEditable(true);
                }

                if (textSearch.getText().isEmpty()) {
                    listFound.setVisible(false);

                } else {
                    listFound.setVisible(true);
                    List<String> listWord = mySQL.searchFromDB(textSearch.getText());
                    ObservableList<String> observableList = FXCollections.observableArrayList(listWord);

                    listFound.setPrefHeight(Math.min(24 * listWord.size(), 235));
//                    listFound.setFocusTraversable(false);
                    listFound.setItems(observableList);
                }
            }
        });
    }

    public void controllerMark() {
        markButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            needReloadBookmark = true;
            System.out.println(wordSelected);

            try {
                if (!mySQL.checkBookmark(wordSelected)) {
                    mySQL.addBookmark(wordSelected);

                    mark(true);
                } else {
                    mySQL.deleteBookmark(wordSelected);

                    mark(false);
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        });
    }

    public void controllerDelete() {
        deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want delete word : \"" + wordSelected + "\"");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                try {
                    mySQL.deleteFromDB(wordSelected);
                    lookup(wordSelected);

                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
    }

    public void controllerEdit() {
        editButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            result.setVisible(false);
            editor.setVisible(true);

            markButton.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
            changeButton.setVisible(true);
            cancelChangeButton.setVisible(true);

            htmlEditor.setHtmlText(mySQL.htmlSelectFromDB(wordSelected));
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
            changeButton.setVisible(false);
            cancelChangeButton.setVisible(false);

            Document doc = Jsoup.parse(htmlEditor.getHtmlText());
            String htmlTextToDB = doc.body().html().replace("\"", "\\\"");
            System.out.println(doc.body().html());
            try {
                mySQL.updateDB(wordSelected, htmlTextToDB);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }

            lookup(wordSelected);
        });

        cancelChangeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            editor.setVisible(false);
            result.setVisible(true);

            changeButton.setVisible(false);
            cancelChangeButton.setVisible(false);

            lookup(wordSelected);
        });
    }

    private void lookup(String word) {
        if (mySQL.selectFromDB(word) == null) {
            markButton.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
        } else {
            wordSelected = word;
            markButton.setVisible(true);
            deleteButton.setVisible(true);
            editButton.setVisible(true);

            if(mySQL.checkBookmark(word)) mark(true);
            else mark(false);
        }

        listFound.setVisible(false);
        listFound.getSelectionModel().select(-1);

        if (textSearch.getText().isEmpty()) {
            result.setVisible(false);
            return;
        }

        String definition = mySQL.htmlSelectFromDB(word);
//        System.out.println(definition);
        result.setVisible(true);
        Platform.runLater(() -> webView.getEngine().loadContent(definition));

    }

    public void loadLoginPage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/views/loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Login Dictionary");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);

    }

    public void loadGamePage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("/views/gameAventure.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        scene.setFill(Color.TRANSPARENT);

        stage.setTitle("Game");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(scene);

    }

    private void mark(boolean isMarked) {
        if (isMarked) {
            markButton.setText("Marked");
            markButton.getStyleClass().clear();
            markButton.getStyleClass().add("button");
            markButton.getStyleClass().add("button2");
            imgMark.setImage(imageMarked);
        } else {
            markButton.setText("Mark");
            markButton.getStyleClass().clear();
            markButton.getStyleClass().add("button");
            markButton.getStyleClass().add("button1");
            imgMark.setImage(imageUnmarked);
        }
    }
}