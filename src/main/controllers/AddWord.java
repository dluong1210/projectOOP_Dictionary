package controllers;

import Application.MySQL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controllerCheck();
        controllerDefine();
    }

    public void controllerCheck() {
        checkButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (textNewWord.getText().isEmpty()) {
                reponseText.setText("Please enter a word in the box");
                reponseText.setFill(Color.RED);
            } else if (checkExist(textNewWord.getText())) {
                reponseText.setText("Word exist. If you want edit that, search and edit it !");
                reponseText.setFill(Color.RED);
            } else {
                reponseText.setText("Wow!!! A new word. Please tell me it's definition");
                reponseText.setFill(Color.GREEN);
                textNewWord.setEditable(false);
                definitionPane.setVisible(true);
            }
        });

        textNewWord.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (definitionPane.isVisible()) {
                reponseText.setText("Please complete this word first");
                reponseText.setFill(Color.RED);
            }
        });
    }

    public void controllerDefine() {
        Platform.runLater(() -> {
            Node[] nodes = htmlEditor.lookupAll(".tool-bar").toArray(new Node[0]);
            for (Node node : nodes) {
                node.setVisible(false);
                node.setManaged(false);
            }
            htmlEditor.setVisible(true);
        });
        htmlEditor.setHtmlText(MySQL.htmlization(""));

        addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Document doc = Jsoup.parse(htmlEditor.getHtmlText());
            String htmlTextToDB = doc.body().html().replace("\"", "\\\"");
            System.out.println(doc.body().html());

            try {
                MySQL.insertIntoDB(textNewWord.getText(), htmlTextToDB);
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }

            reponseText.setText("Add \"" + textNewWord.getText() + "\" successfully");
            reponseText.setFill(Color.GREEN);

            definitionPane.setVisible(false);
            textNewWord.clear();
            textNewWord.setEditable(true);
        });

        cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            reponseText.setText("Please enter a word in the box");
            reponseText.setFill(Color.RED);

            definitionPane.setVisible(false);
            textNewWord.clear();
            textNewWord.setEditable(true);
        });
    }

    private boolean checkExist(String word) {
        return MySQL.selectFromDB(word) != null;
    }
}
