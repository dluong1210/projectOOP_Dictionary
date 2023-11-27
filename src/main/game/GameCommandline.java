package game;

import java.util.Scanner;

/**
 * This game can be play in commandline!
 */
public interface GameCommandline {
    /**
     * Print the question and choices.
     */
    void printQuestionAndChoices();

    /**
     * Print the game rule.
     */
    void printRule();

    /**
     * Start next play turn.
     */
    void startGame();

    /**
     * Print the points then end game.
     */
    void endGame();

    /**
     * Run the game.
     */
    void gameCommandline();

    /**
     * Get the input from the player until it is valid.
     * @param validInput domain of the valid input
     * @param whatIsYourChoice ask the player to make decision
     * @return the valid input from the player
     */
    static String getValidInput(String[] validInput, String whatIsYourChoice) {
        Scanner scan = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println(whatIsYourChoice);
            input = scan.nextLine();
            for (String validString : validInput) {
                if (input.equals(validString)) {
                    return validString;
                }
            }
            System.out.print("Please choose [");
            for (int i = 0; i < validInput.length - 1; i += 1) {
                System.out.print(validInput[i] + "/");
            }

            System.out.print(validInput[validInput.length - 1] + "] \n");
        }
    }
}
