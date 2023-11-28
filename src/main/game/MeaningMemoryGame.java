package game;
public class MeaningMemoryGame extends MemoryGame {

    @Override
    public String giveRule() {
        return " + Memory Meaning Game's rule:\n"
                + "   - Choose an option that contains the meaning of the word given by the previous question.\n"
                + "   - Below is the first question, you don't need to answer it yet. Just remember it!\n\n"
                + "  Question 0: " + words[correctChoice].getWord_target();
    }

    @Override
    public String giveQuestion() {
        return "QUESTION " + (points + 1) + ": \t" + words[correctChoice].getWord_target();
    }

    @Override
    public String giveChoice(int i) {
        return option[i] + ". " + previousWordChoices[i].getWord_explain();
    }
}