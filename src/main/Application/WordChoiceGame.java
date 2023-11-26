package Application;

/**
 * Choose the word has the given meaning.
 */
public class WordChoiceGame extends ChoiceGame {

    @Override
    public String giveRule() {
        return " + Choosing Word Game's rule:\n"
                + "   - Choose an option which contains the word has the meaning given by the question.";
    }

    @Override
    public String giveQuestion() {
        return "Question " + (points + 1) + ": " + words[correctChoice].getWord_explain();
        //return "Question " + (points + 1) + ": " + words[correctChoice].getWord_explain();

    }

    @Override
    public String giveChoice(int i) {
        return option[i] + ". " + words[i].getWord_target();
    }

//// Old method.

/*
    public static void main(String[] args) {
        WordChoiceGame huy = new WordChoiceGame();
        huy.gameCommandline();
    }
*/
}
