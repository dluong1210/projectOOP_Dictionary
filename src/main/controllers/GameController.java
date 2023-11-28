package controllers;

import game.*;
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
    private Button EliminateOne = new Button();
    @FXML
    private Button SecondChance = new Button();
    @FXML
    private Button FiftyFifty = new Button();

    private MultipleChoiceGame game;
    private EliminateOneGameItem eliminateOne = new EliminateOneGameItem();
    private SecondChanceGameItem secondChance = new SecondChanceGameItem();
    private FiftyFiftyGameItem fiftyFifty = new FiftyFiftyGameItem();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printChooseGame();
    }

    /**
     * First screen to choose a type of game.
     */
    public void printChooseGame() {
        setQuestionAndChoicesVisible(true);

        setVisibleItem(false);

        Question.setText("\t  Which game do you want to play?");
        A.setText("\t\t   Choosing Word Game");
        B.setText("\t\t   Memory Word Game");
        C.setText("\t\t   Choosing Meaning Game");
        D.setText("\t\t   Memory Meaning Game");

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
     * <p> Choice A ---- Item </p> <p> Choice B ---- Item </p> <p> Choice C ---- Item</p> <p> Choice D </p>
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
        setVisibleItem(b);
        setDisableItem(!b);
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
        // San sang
        initItem();
        game.initGame();
        printIntroduceAndPlay();                                          // in luat
    }

    /**
     * Introduction and play.
     */
    public void printIntroduceAndPlay() {
        setQuestionAndChoicesVisible(false);
        Introduce.setText("HOW TO PLAY?\n" + getItemInstruction() + game.giveRule());
        Play.setText("Play!");
        Play.setOnAction(e -> startGame());
    }

    /**
     * Start game.
     */
    public void startGame() {
        increaseItem();
        game.getPrepared();
        printQuestionAndChoices();                          // in ra man hinh
    }

    /**
     * Question and choices.
     */
    public void printQuestionAndChoices() {
        setQuestionAndChoicesVisible(true);

        // Noi dung cac lua chon
        Question.setText(game.giveQuestion());
        A.setText(game.giveChoice(0));
        B.setText(game.giveChoice(1));
        C.setText(game.giveChoice(2));
        D.setText(game.giveChoice(3));

        // Cac lua chon
        A.setOnAction(e -> getClickInput("A"));
        B.setOnAction(e -> getClickInput("B"));
        C.setOnAction(e -> getClickInput("C"));
        D.setOnAction(e -> getClickInput("D"));

        // Noi dung item
        setItemText();

        // Neu su dung item
        EliminateOne.setOnAction(e -> setIncorrectChoicesInvisible(eliminateOne.used(game)));
        SecondChance.setOnAction(e -> setIncorrectChoicesInvisible(secondChance.used(game)));
        FiftyFifty.setOnAction(e -> setIncorrectChoicesInvisible(fiftyFifty.used(game)));
    }

    /**
     * Eliminate incorrect choice given by the item.
     * @param eliminate an array of choice need to be eliminated
     */
    public void setIncorrectChoicesInvisible(boolean[] eliminate) {
        setDisableItem(true);
        setItemText();

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
                secondChance.setInvincible(false);
            }
            startGame();                                    // bat dau luot choi moi
        } else {
            if (secondChance.isInvincible()) {             // dang su dung item second chance
                setIncorrectChoicesInvisible(secondChance.used(game));
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
        Question.setVisible(true);
        Introduce.setVisible(false);
        Question.setText("\n\n\n\t\t" + game.giveResult());
        Play.setText("Play again!");
        Play.setOnAction(e -> printChooseGame());
    }

//// Item management method

    /**
     * Set text for all Item.
     */
    public void setItemText() {
        EliminateOne.setText(eliminateOne.getText());
        SecondChance.setText(secondChance.getText());
        FiftyFifty.setText(fiftyFifty.getText());
    }

    /**
     * Increase item usage when time comes.
     */
    public void increaseItem() {
        eliminateOne.increaseUsage(game.getPoints(), 2);
        secondChance.increaseUsage(game.getPoints(), 4);
        fiftyFifty.increaseUsage(game.getPoints(), 6);
    }

    /**
     * Set disable for all Item.
     * @param b switch to disable
     */
    public void setDisableItem(boolean b) {
        EliminateOne.setDisable(b);
        SecondChance.setDisable(b);
        FiftyFifty.setDisable(b);
    }

    /**
     * Set visible for all Item (unusable item must be invisible).
     * @param b switch to visible
     */
    public void setVisibleItem(boolean b) {
        EliminateOne.setVisible(eliminateOne.isUsable() && b);
        SecondChance.setVisible(secondChance.isUsable() && b);
        FiftyFifty.setVisible(fiftyFifty.isUsable() && b);
    }

    /**
     * How to use Item?
     * @return guide to use Item
     */
    public String getItemInstruction() {
        return " + Item:\n"
                + "   - To get help for the hard question, choose an item before choose any option."
                + " A question can't use more than one item so think carefully what to choose.\n"
                + eliminateOne.getInstruction()
                + secondChance.getInstruction()
                + fiftyFifty.getInstruction();
    }

    /**
     * Set number of uses back to 0.
     */
    public void initItem() {
        eliminateOne.setNumber(0);
        secondChance.setNumber(0);
        fiftyFifty.setNumber(0);
    }
}
