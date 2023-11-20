package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class GameCommandLine extends MultipleChoiceGame{
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

    private static String getValidInput(Scanner scan) {
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

    public void gameCommandline() {
        String inputChoice;
        Scanner scan = new Scanner(System.in);
        setPoints(0);
        do {
            initGame();
            printQuestion();
            inputChoice = getValidInput(scan);
            setPlayerAnswer(inputChoice);
        } while (checkAnswer());
    }
}