package Application;

import java.util.Scanner;

public abstract class GameCommandLine extends MultipleChoiceGame{
    public void printQuestion() {
        System.out.println(giveQuestion());
        for (int i = 0; i < 4; i += 1) {
            System.out.println(giveChoice(i));
        }
    }

    public boolean checkAnswer() {
        if (playerChoice != correctChoice) {
            System.out.println(endGame());
            return false;
        } else {
            setPoints(getPoints() + 1);
        }
        return true;
    }

    public String getValidInput(Scanner scan) {
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

    public Word[] getWordChoices() {
        int random = (int) (Math.random() * 4);
        setCorrectChoice(random);                                               // chon 1 lua chon se la dap an dung
        Word[] words = new Word[4];

        for (int i = 0; i < 4; i++) {
            words[i] = getValidWord(i, words);
        }
        return words;
    }

    public Word getValidWord(int i, Word[] words) {
        Word choiceWord = getRandomWord();
        boolean pickAgain = true;

        while (pickAgain) {
            choiceWord = getRandomWord();
            pickAgain = false;                                   // tim tu thoa man rang buoc

            for (int j = 0; j < i; j += 1) {
                if (choiceWord.equals(words[j])) {
                    pickAgain = true;
                    break;
                }
            }
        }
        return choiceWord;
    }


    public void gameCommandline() {
        String inputChoice;
        Scanner scan = new Scanner(System.in);
        setPoints(0);
        dictionaryManagement.insertFromFile();                                  // tao database
        do {
            Word[] words = getWordChoices();
            setChoicesAndQuestion(words);
            printQuestion();
            inputChoice = getValidInput(scan);
            setPlayerAnswer(inputChoice);
        } while (checkAnswer());
    }
}
