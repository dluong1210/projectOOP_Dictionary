package Application;

/**
 * Choose the meaning of the given word.
 */
public class MeaningChoiceGame extends ChoiceGame {

    @Override
    public String giveRule() {
        return "Choose the option contains the meaning of the word given by the question.";
    }

    @Override
    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWhich word has the following meaning?: " + words[correctChoice].getWord_target();
    }

    @Override
    public String giveChoice(int i) {
        return option[i] + ". " + words[i].getWord_explain();
    }

//// Old method.

/*
    public static void main(String[] args) {
        MeaningChoiceGame huy = new MeaningChoiceGame();
        huy.gameCommandline();
    }
 */

}


