package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Learning English with multiple-choice game choosing A B C D.
 */
public abstract class MultipleChoiceGame implements GameCommandline {
    //protected DictionaryManagement dictionaryManagement = new DictionaryManagement();

    protected List<Word> wordDatabase = new ArrayList<>();
    /**
     * Four options A B C D.
     */
    protected static final String[] option = {"A", "B", "C", "D"};
    /**
     * Four words using to generate question and choices.
     */
    protected Word[] words = new Word[4];
    /**
     * Player choice from input.
     */
    protected String playerChoice;
    /**
     * The nth option is the correct choice.
     */
    protected int correctChoice;
    /**
     * Player points.
     */
    protected int points;

    /**
     * Prepare for the game.
     */
    public abstract void initGame();

    /**
     * Insert the Word Database into a list.
     */
    public void getWordDatabase() {
        try {
            File input = new File("src/resources/data/wordDatabaseForGame.txt");
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String[] word = scan.nextLine().split("\t");
                wordDatabase.add(new Word(word[0], word[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * How to play.
     * @return the game's rule
     */
    public abstract String giveRule();

    /**
     * Prepare question and choices for the current turn.
     */
    public abstract void getPrepared();

    /**
     * A random word with chosen length.
     * @return a random word with length from 4 to 16 characters
     */
    protected Word getRandomWord() {
        int sizeOfDatabase = wordDatabase.size();
        int random = (int) (Math.random() * sizeOfDatabase);
        return wordDatabase.get(random);
    }

    /**
     * Update four words with condition.
     */
    public abstract void getValidWordChoices();

    /**
     * The full question generated by the correctChoice word.
     * @return the full question generated by the correctChoice word
     */
    public abstract String giveQuestion();

    /**
     * The ith choice generated by the ith word.
     * @param i the ith option
     * @return the full choice sentence
     */
    public abstract String giveChoice(int i);

    /**
     * Compare the player's choice with the correct choice.
     * @return true if the player's choice equal the correct option then add point
     */
    public boolean checkAnswer() {
        if (playerChoice.equals(option[correctChoice])) {
            setPoints(getPoints() + 1);
            return true;
        }
        return false;
    }

    /**
     * The points player has achieved.
     * @return the result
     */
    public String giveResult() {
        return "Congratulation! Your points is: " + points;
    }

//////// Game Commandline method.

    @Override
    public void printQuestionAndChoices() {
        System.out.println(giveQuestion());
        for (int i = 0; i < 4; i += 1) {
            System.out.println(giveChoice(i));
        }
    }

    @Override
    public void startGame() {
        getPrepared();
        printQuestionAndChoices();                          // in ra man hinh
        setPlayerChoice(GameCommandline.getValidInput(option, "Your answer is: "));     // nhan input tu nguoi choi
        if (checkAnswer()) {
            startGame();                                    // bat dau luot choi moi
        } else {
            endGame();                                      // bao diem ket thuc game
        }
    }

    @Override
    public void endGame() {
        System.out.println(giveResult());
    }

    @Override
    public void printRule() {
        System.out.println(giveRule());
        String[] ready = new String[1];
        ready[0] = "1";
        GameCommandline.getValidInput(ready, "Are you ready?\n[1] YES" );
    }

    @Override
    public void gameCommandline() {
        initGame();
        printRule();                                          // in luat
        startGame();                                          // bat dau luot choi moi
    }

//////// Getter / setter


    public void setWordDatabase(List<Word> wordDatabase) {
        this.wordDatabase = wordDatabase;
    }

    public static String[] getOption() {
        return option;
    }

    public Word[] getWords() {
        return words;
    }

    public void setWords(Word[] words) {
        this.words = words;
    }

    public String getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(String playerChoice) {
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
