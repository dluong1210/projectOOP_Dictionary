package Application;

/**
 * Choose the meaning of the given word.
 */
public class MeaningChoiceGame extends ChoiceGame {

    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWhich word has the following meaning?: " + words[correctChoice].getWord_target();
    }

    public String giveChoice(int i) {
        return option[i] + ". " + words[i].getWord_explain();
    }

//////// Game Commandline method.

    public void printRule() {
        System.out.println("Choose the option contains the meaning of the word given by the question.");
    }

    public static void main(String[] args) {
        MeaningChoiceGame huy = new MeaningChoiceGame();
        huy.gameCommandline();
    }

}


