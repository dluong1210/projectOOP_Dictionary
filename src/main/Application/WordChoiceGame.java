package Application;

/**
 * Choose the word has the given meaning.
 */
public class WordChoiceGame extends ChoiceGame {

    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWhich word has the following meaning?: " + words[correctChoice].getWord_explain();
    }

    public String giveChoice(int i) {
        return option[i] + ". " + words[i].getWord_target();
    }

//////// Game Commandline method.
    public void printRule() {
        System.out.println("Choose the option contains the word has the meaning given by the question.");
    }

    public static void main(String[] args) {
        WordChoiceGame huy = new WordChoiceGame();
        huy.gameCommandline();
    }

}
