package Application;
public class WordMemoryGame extends MemoryGame {

    public String giveRule() {
        return "Choose the option contains the word has the meaning given by the previous question.\n"
                + "This is the first question. Remember it!\n"
                + "Question 0: \nMeaning: " + words[correctChoice].getWord_explain();
    }

    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nMeaning: " + words[correctChoice].getWord_explain();
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


