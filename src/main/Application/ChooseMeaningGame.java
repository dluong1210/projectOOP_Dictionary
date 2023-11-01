package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Game chon tu mang nghia trong cau hoi.
 */
public class ChooseMeaningGame {

    DictionaryManagement dictionaryManagement = new DictionaryManagement();

    long seed = System.currentTimeMillis();
    Random random = new Random(seed);

    private String question;
    private String[] choices = new String[4];
    private int playerChoice;
    private int correctChoice;
    private int points;

    public String endGame() {
        return "Congratulation! Your points is: " + getPoints();
    }

    public String correctAnswer() {
        switch (correctChoice) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            case 3:
                return "D";
            default:
                return "Something went wrong here!";
        }
    }

    public String giveQuestion() {
        return "Which word has the following meaning?: " + question;
    }

    public boolean checkAnswer() {
        return playerChoice == correctChoice;                                   // khong can thiet hihi
    }

    public boolean validAnswer(String answer) {
        return true;                                                            // de gioi han tap dap an ma chua can den
    }

    public Word getRandomWord() {
        dictionaryManagement.insertFromFile();                                  // tao database
        //int databseSize = dictionaryManagement.listWord.size();
        int databseSize = 108821;
        int random = (int) (Math.random() * databseSize);                       // lay random * size database
        //Word choiceWord = dictionaryManagement.listWord.getWord(random);      // database.getWord(random) tra ve Word
        return null;                                                            // tra ve Word random lay trong database
    }

    public void initGame() {
        int random = (int) (Math.random() * 4);
        setCorrectChoice(random);                                               // chon 1 lua chon se la dap an dung
        /*
        for (int i = 0; i < 4; i++) {
            Word choiceWord = getRandomWord();
            while (!(validAnswer(choiceWord.getWord_target()) && validAnswer(choiceWord.getWord_explain()))) {
                choiceWord = getRandomWord();                                   // tim tu thoa man rang buoc
            }
            choices[i] = choiceWord.getWord_target();                           // tao cac lua chon
            if (i == random) {
                setQuestion(choiceWord.getWord_explain());                      // tao cau hoi
            }
        }

         */
        // Thu trong luc chua co ham random

        Word w0 = new Word("huy", "super cuteeee");
        Word w1 = new Word("cat", "so cute");
        Word w2 = new Word("OASIS", "ac mong");
        Word w3 = new Word("Game cua TVDH", "10 diem");

        List<Word> test = new ArrayList<>();
        test.add(w0);
        test.add(w1);
        test.add(w2);
        test.add(w3);
        for (int i = 0; i < 4; i++) {
            choices[i] = test.get(i).getWord_target();                           // tao cac lua chon
            if (i == random) {
                setQuestion(test.get(i).getWord_explain());                      // tao cau hoi
            }
        }
    }

    public void gameCommandline() {
        String inputChoice;
        Scanner scan = new Scanner(System.in);
        setPoints(0);

        while (true) {
            initGame();
            System.out.println(giveQuestion());
            System.out.println("A. " + choices[0]);
            System.out.println("B. " + choices[1]);
            System.out.println("C. " + choices[2]);
            System.out.println("D. " + choices[3]);

            while (true) {
                System.out.println("Your answer is: ");
                inputChoice = scan.nextLine();
                if (!(inputChoice.equals("A") || inputChoice.equals("B") || inputChoice.equals("C") || inputChoice.equals("D"))) {
                    System.out.println("Please choose A, B, C or D");
                    continue;
                }
                break;
            }
            switch (inputChoice) {
                case "A":
                    setPlayerChoice(0);
                    break;
                case "B":
                    setPlayerChoice(1);
                    break;
                case "C":
                    setPlayerChoice(2);
                    break;
                case "D":
                    setPlayerChoice(3);
                    break;
                default:
                    System.out.println("Please choose A, B, C or D");
                    break;
            }
            if (!checkAnswer()) {
                System.out.println(endGame());
                break;
            } else {
                setPoints(getPoints() + 1);
            }
        }
    }

    public static void main(String[] args) {
        ChooseMeaningGame huy = new ChooseMeaningGame();
        huy.gameCommandline();
    }













































// Getter / setter
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
