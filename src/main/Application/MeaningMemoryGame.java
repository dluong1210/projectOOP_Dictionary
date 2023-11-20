package Application;
public class MeaningMemoryGame extends MemoryGame {
    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWord: " + words[correctChoice].getWord_target();
    }

    public String giveChoice(int i) {
        return option[i] + ". " + previousWordChoices[i].getWord_explain();
    }

//////// Game Commandline method.

    @Override
    public void printRule() {
        System.out.println("Choose the option contains the meaning of the word given by the previous question.");
        System.out.println("This is the first question. Remember it!\n");
        System.out.println("Word: " + words[correctChoice].getWord_target());
        String[] ready = new String[1];
        ready[0] = "1";
        GameCommandline.getValidInput(ready, "Are you ready?\n[1] YES" );
    }

    public static void main(String[] args) {
        MeaningMemoryGame huy = new MeaningMemoryGame();
        huy.gameCommandline();
    }

}