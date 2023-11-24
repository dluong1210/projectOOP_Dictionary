package controllers;

import Application.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Button A = new Button();
    @FXML
    private Button B = new Button();
    @FXML
    private Button C = new Button();
    @FXML
    private Button D = new Button();
    @FXML
    private Button Play = new Button();
    @FXML
    private Text Question = new Text();
    @FXML
    private Text Introduce = new Text();
    private MultipleChoiceGame game;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printChooseGame();
    }

    /**
     * Swich screen to question and choices screen.
     *
     * <p> Question and choices screen includes: </p>
     * <p> Question </p>
     * <p> Choice A </p>
     * <p> Choice B </p>
     * <p> Choice C </p>
     * <p> Choice D </p>
     *
     * <p> Introduce and Play screen includes: </p>
     * <p> Introduce </p>
     * <p> Choice Play </p>
     * @param b is true if you want to change to screen QaC,
     *          false if you want change to screen IaP
     */
    private void setQuestionAndChoicesVisible(boolean b) {
        // Introduce and Play screen
        Introduce.setVisible(!b);
        Play.setVisible(!b);

        // Question and choices screen
        Question.setVisible(b);
        A.setVisible(b);
        B.setVisible(b);
        C.setVisible(b);
        D.setVisible(b);
    }

    /**
     * First screen to choose a type of game.
     */
    public void printChooseGame() {
        setQuestionAndChoicesVisible(true);

        Question.setText("Which game would you like to play?");
        A.setText("Easy Word Learning");
        B.setText("Hard Word Learning");
        C.setText("Easy Meaning Learning");
        D.setText("Hard Meaning Learning");

        A.setOnAction(e -> initGame("A"));
        B.setOnAction(e -> initGame("B"));
        C.setOnAction(e -> initGame("C"));
        D.setOnAction(e -> initGame("D"));
    }

    /**
     * Prepare to start the game.
     * @param gameType type of game
     */
    public void initGame(String gameType) {
        switch (gameType) {
            case "A":
                game = new WordChoiceGame();
                break;
            case "B":
                game = new WordMemoryGame();
                break;
            case "C":
                game = new MeaningChoiceGame();
                break;
            case "D":
                game = new MeaningMemoryGame();
                break;
        }
        game.initGame();
        printIntroduceAndPlay();                                          // in luat
    }

    /**
     * Introduce and play.
     */
    public void printIntroduceAndPlay() {
        setQuestionAndChoicesVisible(false);
        Introduce.setText(game.giveRule());
        Play.setText("Play!");
        Play.setOnAction(e -> startGame());
    }

    /**
     * Start game.
     */
    public void startGame() {
        game.getPrepared();
        printQuestionAndChoices();                          // in ra man hinh
    }

    /**
     * Question and choices.
     */
    public void printQuestionAndChoices() {
        setQuestionAndChoicesVisible(true);

        Question.setText(game.giveQuestion());
        A.setText(game.giveChoice(0));
        B.setText(game.giveChoice(1));
        C.setText(game.giveChoice(2));
        D.setText(game.giveChoice(3));

        A.setOnAction(e -> getClickInput("A"));
        B.setOnAction(e -> getClickInput("B"));
        C.setOnAction(e -> getClickInput("C"));
        D.setOnAction(e -> getClickInput("D"));
    }

    /**
     * Get player's choice.
     * @param playerChoice the player's choice
     */
    public void getClickInput(String playerChoice) {
        game.setPlayerChoice(playerChoice);
        if (game.checkAnswer()) {
            startGame();                                    // bat dau luot choi moi
        } else {
            endGame();                                      // bao diem ket thuc game
        }
    }

    /**
     * Show the result.
     */
    public void endGame() {
        setQuestionAndChoicesVisible(false);
        Introduce.setText(game.giveResult());
        Play.setText("Play again!");
        Play.setOnAction(e -> printChooseGame());
    }
}
