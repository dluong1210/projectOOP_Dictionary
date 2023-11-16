package Application;

import java.util.Scanner;

public class MemoryMeaningGame extends GameCommandLine{
    protected Word nextWord;


    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWord: " + question;
    }

    public Word getRandomWord() {
        return dictionaryManagement.listWord.getRandomWord(1,1);                                                            // tra ve Word random lay trong database
    }

    public void setChoicesAndQuestion(Word[] words) {
        int nextRandom = (int) (Math.random() * 4);
        for (int i = 0; i < 4; i++) {
            choices[i] = words[i].getWord_explain();                           // tao cac lua chon
        }
        setQuestion(words[correctChoice].getWord_target());                      // tao cau hoi
        nextWord = words[nextRandom];
    }

    public Word[] getWordChoices() {
        int random = (int) (Math.random() * 4);
        setCorrectChoice(random);                                               // chon 1 lua chon se la dap an dung

        Word[] words = new Word[4];
        for (int i = 0; i < 4; i++) {
            words[i] = getValidWord(i, words);
        }
        if (points > 0) {
            words[correctChoice] = nextWord;
        }
        return words;
    }

    public Word getValidWord(int i, Word[] words) {
        Word choiceWord = getRandomWord();
        boolean pickAgain = true;

        while (pickAgain) {
            choiceWord = getRandomWord();
            pickAgain = false;                                   // tim tu thoa man rang buoc
            if (points > 0) {
                pickAgain = choiceWord.equals(nextWord);
            }
            for (int j = 0; j < i; j += 1) {
                if (choiceWord.equals(words[j])) {
                    pickAgain = true;
                    break;
                }
            }
        }
        return choiceWord;
    }

    public int copyChoices(String[] paste, String[] copy, int correctCopy) {
        for (int i = 0; i < 4; i += 1) {
            paste[i] = copy[i];
        }
        return correctCopy;
    }

    public int switchChoices(String[] previousChoices, int previousCorrectChoice) {
        String[] auxChoices = new String[4];
        int auxCorrectChoice;

        auxCorrectChoice = copyChoices(auxChoices, previousChoices, previousCorrectChoice);
        previousCorrectChoice = copyChoices(previousChoices, choices, correctChoice);
        correctChoice = copyChoices(choices, auxChoices, auxCorrectChoice);

        return previousCorrectChoice;
    }

    public void gameCommandline() {
        String inputChoice;
        Scanner scan = new Scanner(System.in);
        setPoints(0);
        dictionaryManagement.insertFromFile();                                  // tao database

        Word[] words = getWordChoices();
        setChoicesAndQuestion(words);
        String[] previousChoices = choices;
        int previousCorrectChoice = correctChoice;

        System.out.println("Meaning: " + question + "\n");
        waitingToReady(scan);
        do {
            words = getWordChoices();
            setChoicesAndQuestion(words);
            previousCorrectChoice = switchChoices(previousChoices, previousCorrectChoice);
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
        MemoryMeaningGame huy = new MemoryMeaningGame();
        huy.gameCommandline();
    }


    public void setNextWord(Word nextWord) {
        this.nextWord = nextWord;
    }

    public Word getNextWord() {
        return nextWord;
    }
}
