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
        return "QUESTION " + (points + 1) + ": \t" + words[correctChoice].getWord_explain();
    }

    @Override
    public String giveChoice(int i) {
        return option[i] + ". " + words[i].getWord_target();
    }
}
