package Application;

import java.util.Scanner;

public class MemoryWordGame extends MultipleChoiceGame{

    protected String[] previousChoices = new String[4];

    protected int previousCorrectChoice;

    protected Word nextWord;


    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nMeaning: " + question;
    }

    public Word getRandomWord() {
        return dictionaryManagement.listWord.getRandomWord(1,1);                                                            // tra ve Word random lay trong database
    }

    public void initGame() {
        dictionaryManagement.insertFromFile();                                  // tao database
        int random = (int) (Math.random() * 4);
        setCorrectChoice(random);                                               // chon 1 lua chon se la dap an dung
        int nextRandom = (int) (Math.random() * 4);
        Word auxWord = new Word("0", "0");

        for (int i = 0; i < 4; i++) {
            Word choiceWord = getRandomWord();
            boolean pickAgain = true;

            while (pickAgain) {
                choiceWord = getRandomWord();
                pickAgain = false;                                   // tim tu thoa man rang buoc
                if (points > 0) {
                    pickAgain = choiceWord.getWord_target().equals(nextWord.getWord_target());
                }
                for (int j = 0; j < i; j += 1) {
                    if (choiceWord.getWord_target().equals(choices[j])) {
                        pickAgain = true;
                        break;
                    }
                }
            }

            choices[i] = choiceWord.getWord_target();                           // tao cac lua chon
            if (i == random) {
                if (points == 0) {
                    setQuestion(choiceWord.getWord_explain());                      // tao cau hoi
                } else {
                    choiceWord = nextWord;
                    choices[i] = choiceWord.getWord_target();
                    setQuestion(choiceWord.getWord_explain());                      // tao cau hoi
                }

            }
            if (i == nextRandom) {
                auxWord = choiceWord;
            }
        }
        nextWord = auxWord;
    }

    public void printQuestion() {
        System.out.println(giveQuestion());
        for (int i = 0; i < 4; i += 1) {
            System.out.println(giveChoice(i));
        }
    }

    private boolean checkAnswer() {
        if (playerChoice != correctChoice) {
            System.out.println(endGame());
            return false;
        } else {
            setPoints(getPoints() + 1);
        }
        return true;
    }

    private String getValidInput(Scanner scan) {
        String inputChoice;
        while (true) {
            System.out.println("Your answer is: ");
            inputChoice = scan.nextLine();
            if (!(inputChoice.equals("A") || inputChoice.equals("B") || inputChoice.equals("C") || inputChoice.equals("D"))) {
                System.out.println("Please choose A, B, C or D");
                continue;
            }
            break;
        }
        return inputChoice;
    }

    public void copyChoices(String[] paste, String[] copy) {
        for (int i = 0; i < 4; i += 1) {
            paste[i] = copy[i];
        }
    }

    public void switchChoices(String[] choices1, String[] choices2, int correct1, int correct2) {
        String[] auxChoices = new String[4];
        int auxCorrectChoice;

        copyChoices(auxChoices, previousChoices);
        auxCorrectChoice = previousCorrectChoice;

        copyChoices(previousChoices, choices);
        previousCorrectChoice = correctChoice;

        copyChoices(choices, auxChoices);
        correctChoice = auxCorrectChoice;
    }

    public void gameCommandline() {
        String inputChoice;
        Scanner scan = new Scanner(System.in);
        setPoints(0);
        initGame();
        copyChoices(previousChoices, choices);
        previousCorrectChoice = correctChoice;
        System.out.println("Meaning: " + question + "\n");
        waitingToReady(scan);
        do {
            initGame();
            switchChoices(previousChoices, choices, previousCorrectChoice, correctChoice);
            printQuestion();
            inputChoice = getValidInput(scan);
            setPlayerAnswer(inputChoice);
        } while (checkAnswer());
    }

    private void waitingToReady(Scanner scan) {
        String inputChoice;
        while (true) {
            System.out.println("Are you ready?\n[1] YES");
            inputChoice = scan.nextLine();
            if (!inputChoice.equals("1")) {
                System.out.println("Please choose 1 if you are ready");
                continue;
            }
            break;
        }
    }

    public static void main(String[] args) {
        MemoryWordGame huy = new MemoryWordGame();
        huy.gameCommandline();
    }











    public String[] getPreviousChoices() {
        return previousChoices;
    }

    public void setPreviousChoices(String[] previousChoices) {
        this.previousChoices = previousChoices;
    }
}