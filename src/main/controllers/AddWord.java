package controllers;

import application.MySQL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddWord implements Initializable {
    @FXML
    private TextField textNewWord;
    @FXML
    private Button checkButton;
    @FXML
    private Text reponseText;
    @FXML
    private BorderPane definitionPane;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    private MySQL mySQL = MySQL.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controllerCheck();
        controllerAdd();
    }

    public void controllerCheck() {
        checkButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> check());

        textNewWord.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                check();
            }
        });

        textNewWord.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (definitionPane.isVisible()) {
                reponseText.setText("Please complete this word first");
                reponseText.setFill(Color.RED);
            }
        });
    }

    private void check() {
        if (textNewWord.getText().isEmpty()) {
            reponseText.setText("Please enter a word in the box");
            reponseText.setFill(Color.RED);

        } else if (checkExist(textNewWord.getText())) {
            reponseText.setText("Word exist. If you want edit that, search and edit it !");
            reponseText.setFill(Color.RED);

        } else {
            reponseText.setText("It's new word. Please tell me it's definition");
            reponseText.setFill(Color.GREEN);
            textNewWord.setEditable(false);
            definitionPane.setVisible(true);
            cancelButton.setVisible(true);
            addButton.setVisible(true);

            Platform.runLater(() -> {
                Node[] nodes = htmlEditor.lookupAll(".tool-bar").toArray(new Node[0]);
                for (Node node : nodes) {
                    node.setVisible(false);
                    node.setManaged(false);
                }
                htmlEditor.setVisible(true);
            });
            htmlEditor.setHtmlText(mySQL.htmlization(""));

        }
    }

    public void controllerAdd() {

        addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Document doc = Jsoup.parse(htmlEditor.getHtmlText());
            String htmlTextToDB = doc.body().html().replace("\"", "\\\"");
            System.out.println(doc.body().html());

            try {
                mySQL.insertIntoDB(textNewWord.getText(), htmlTextToDB);

            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }

            reponseText.setText("Add \"" + textNewWord.getText() + "\" successfully. Thanks for your contribution.");
            reponseText.setFill(Color.GREEN);

            definitionPane.setVisible(false);
            cancelButton.setVisible(false);
            addButton.setVisible(false);
            textNewWord.clear();
            textNewWord.setEditable(true);
        });

        cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            reponseText.setText("Please enter a word in the box");
            reponseText.setFill(Color.RED);

            definitionPane.setVisible(false);
            textNewWord.clear();
            cancelButton.setVisible(false);
            addButton.setVisible(false);
            textNewWord.setEditable(true);
        });
    }

    private boolean checkExist(String word) {
        return mySQL.selectFromDB(word) != null;
    }
}
