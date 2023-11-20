package Application;
public class WordMemoryGame extends MemoryGame {
    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nMeaning: " + words[correctChoice].getWord_explain();
    }

    public String giveChoice(int i) {
        return option[i] + ". " + previousWordChoices[i].getWord_target();
    }

//////// Game Commandline method.

    @Override
    public void printRule() {
        System.out.println("Choose the option contains the word has the meaning given by the previous question.");
        System.out.println("This is the first question. Remember it!\n");
        System.out.println("Question 0: \nMeaning: " + words[correctChoice].getWord_explain());
        String[] ready = new String[1];
        ready[0] = "1";
        GameCommandline.getValidInput(ready, "Are you ready?\n[1] YES" );
    }


    public static void main(String[] args) {
        WordMemoryGame huy = new WordMemoryGame();
        huy.gameCommandline();
    }

}


