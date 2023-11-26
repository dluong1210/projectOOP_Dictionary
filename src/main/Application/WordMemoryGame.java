package Application;
public class WordMemoryGame extends MemoryGame {

    public String giveRule() {
        return " + Memory Word Game's rule:\n"
                + "   - Choose the option which contains the word has the meaning given by the previous question.\n"
                + "   - Below is the first question, you don't need to answer it yet. Just remember it!\n\n"
                + "  Question 0: " + words[correctChoice].getWord_explain();
    }

    public String giveQuestion() {
        return "Question " + (points + 1) + ": " + words[correctChoice].getWord_explain();
    }

    public String giveChoice(int i) {
        return option[i] + ". " + previousWordChoices[i].getWord_target();
    }

//// Old method.

/*
    public static void main(String[] args) {
        WordMemoryGame huy = new WordMemoryGame();
        huy.gameCommandline();
    }
 */

}


