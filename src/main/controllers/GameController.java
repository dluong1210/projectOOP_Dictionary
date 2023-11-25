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
    @FXML
    private Button FiftyFifty = new Button();
    @FXML
    private Button EliminateOne = new Button();
    @FXML
    private Button SecondChance = new Button();
    private MultipleChoiceGame game;
    private FiftyFiftyGameItem fiftyFifty = new FiftyFiftyGameItem();
    private EliminateOneGameItem eliminateOne = new EliminateOneGameItem();
    private SecondChanceGameItem secondChance = new SecondChanceGameItem();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printChooseGame();
        FiftyFifty.setVisible(false);
        EliminateOne.setVisible(false);
        SecondChance.setVisible(false);
    }

    /**
     * First screen to choose a type of game.
     */
    public void printChooseGame() {
        setQuestionAndChoicesVisible(true);

        FiftyFifty.setVisible(false);
        EliminateOne.setVisible(false);
        SecondChance.setVisible(false);

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
     * Swich screen to question and choices screen.
     *
     * <p> Question and choices screen includes: </p>
     * <p> Question </p>
     * <p> Choice A </p> <p> Choice B </p>
     * <p> Choice C </p> <p> Choice D </p>
     *
     * <p> Introduce and Play screen includes: </p>
     * <p> Introduce </p> <p> Choice Play </p>
     * @param b is true if you want to change to screen QaC,
     *          false if you want change to screen IaP
     */
    public void setQuestionAndChoicesVisible(boolean b) {
        // Introduce and Play screen
        Introduce.setVisible(!b);
        Play.setVisible(!b);

        // Question and choices screen
        Question.setVisible(b);
        A.setVisible(b);
        B.setVisible(b);
        C.setVisible(b);
        D.setVisible(b);

        A.setDisable(!b);
        B.setDisable(!b);
        C.setDisable(!b);
        D.setDisable(!b);

        // Game item
        FiftyFifty.setVisible(fiftyFifty.isUsable() && b);
        EliminateOne.setVisible(eliminateOne.isUsable() && b);
        SecondChance.setVisible(secondChance.isUsable() && b);

        FiftyFifty.setDisable(!fiftyFifty.isUsable() && b);
        EliminateOne.setDisable(!eliminateOne.isUsable() && b);
        SecondChance.setDisable(!secondChance.isUsable() && b);
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
        // So lan su dung item
        fiftyFifty.setNumber(1);
        eliminateOne.setNumber(1);
        secondChance.setNumber(1);

        // San sang
        game.initGame();
        printIntroduceAndPlay();                                          // in luat
    }

    /**
     * Introduction and play.
     */
    public void printIntroduceAndPlay() {
        setQuestionAndChoicesVisible(false);
        Introduce.setText(game.giveRule());
        // Them chi tiet cach su dung item

        Play.setText("Play!");
        Play.setOnAction(e -> startGame());
    }

    /**
     * Start game.
     */
    public void startGame() {
        // Bonus item
        if (game.getPoints() > 0) {
            if (game.getPoints() % 3 == 0) {
                eliminateOne.setNumber(eliminateOne.getNumber() + 1);
            }
            if (game.getPoints() % 6 == 0) {
                secondChance.setNumber(secondChance.getNumber() + 1);
            }
            if (game.getPoints() % 9 == 0) {
                fiftyFifty.setNumber(fiftyFifty.getNumber() + 1);
            }
        }
        game.getPrepared();
        printQuestionAndChoices();                          // in ra man hinh
    }

    /**
     * Question and choices.
     */
    public void printQuestionAndChoices() {
        setQuestionAndChoicesVisible(true);

        // Noi dung
        Question.setText(game.giveQuestion());
        A.setText(game.giveChoice(0));
        B.setText(game.giveChoice(1));
        C.setText(game.giveChoice(2));
        D.setText(game.giveChoice(3));

        // Neu su dung item
        FiftyFifty.setOnAction(e -> setIncorrectChoicesInvisible(fiftyFifty.used(game)));
        EliminateOne.setOnAction(e -> setIncorrectChoicesInvisible(eliminateOne.used(game)));
        SecondChance.setOnAction(e -> setIncorrectChoicesInvisible(secondChance.used(game)));

        // Cac lua chon
        A.setOnAction(e -> getClickInput("A"));
        B.setOnAction(e -> getClickInput("B"));
        C.setOnAction(e -> getClickInput("C"));
        D.setOnAction(e -> getClickInput("D"));
    }

    /**
     * Eliminate incorrect choice given by the item.
     * @param eliminate an array of choice need to be eliminated
     */
    public void setIncorrectChoicesInvisible(boolean[] eliminate) {
        FiftyFifty.setDisable(true);
        EliminateOne.setDisable(true);
        SecondChance.setDisable(true);

        A.setDisable(eliminate[0]);
        B.setDisable(eliminate[1]);
        C.setDisable(eliminate[2]);
        D.setDisable(eliminate[3]);
    }


    /**
     * Get player's choice.
     * @param playerChoice the player's choice
     */
    public void getClickInput(String playerChoice) {
        game.setPlayerChoice(playerChoice);
        if (game.checkAnswer()) {
            if (secondChance.isInvincible()) {             // dang su dung item second chance
                secondChance.setNumber(secondChance.getNumber() - 1);
                secondChance.setInvincible(false);
            }
            startGame();                                    // bat dau luot choi moi
        } else {
            if (secondChance.isInvincible()) {             // dang su dung item second chance
                printQuestionAndChoices();
                setIncorrectChoicesInvisible(secondChance.used(game));
                secondChance.setInvincible(false);
            } else {
                endGame();                                      // bao diem ket thuc game
            }
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
