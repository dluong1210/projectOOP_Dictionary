package Application;
public class MeaningMemoryGame extends MemoryGame {

    @Override
    public String giveRule() {
        return "Choose the option contains the meaning of the word given by the previous question.\n"
                + "This is the first question. Remember it!\n"
                + "Question 0: \nWord: " + words[correctChoice].getWord_target();
    }

    @Override
    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWord: " + words[correctChoice].getWord_target();
    }

    @Override
    public String giveChoice(int i) {
        return option[i] + ". " + previousWordChoices[i].getWord_explain();
    }

//// Old method.

/*
    public static void main(String[] args) {
        MeaningMemoryGame huy = new MeaningMemoryGame();
        huy.gameCommandline();
    }
 */

}