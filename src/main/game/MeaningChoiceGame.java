package game;

/**
 * Choose the meaning of the given word.
 */
public class MeaningChoiceGame extends ChoiceGame {

    @Override
    public String giveRule() {
        return " + Choosing Meaning Game's rule:\n"
                + "   - Choose an option which contains the meaning of the word given by the question.";
    }

    @Override
    public String giveQuestion() {
        return "QUESTION " + (points + 1) + ":   " + words[correctChoice].getWord_target();
    }

    @Override
    public String giveChoice(int i) {
        return option[i] + ".  " + words[i].getWord_explain();
    }
}


