package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Game chon dap an dung.
 */
public abstract class MultipleChoiceGame {

    protected DictionaryManagement dictionaryManagement = new DictionaryManagement();
    protected String question;
    protected String[] choices = new String[4];
    protected int playerChoice;
    protected int correctChoice;
    protected int points;

    public String endGame() {
        return "Congratulation! Your points is: " + points;
    }

    public String giveQuestion() {
        return "Question " + (points + 1) + ": \n";
    }

    public abstract void initGame();












































    // Getter / setter

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    public void setDictionaryManagement(DictionaryManagement dictionaryManagement) {
        this.dictionaryManagement = dictionaryManagement;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



}
