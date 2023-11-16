package Application;

/**
 * Game chon tu mang nghia trong cau hoi.
 */
public class ChooseWordGame extends GameCommandLine {

    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWhich word has the following meaning?: " + question;
    }

    public Word getRandomWord() {
        return dictionaryManagement.listWord.getRandomWord(1,1);                                                            // tra ve Word random lay trong database
    }

    public void setChoicesAndQuestion(Word[] words) {
        for (int i = 0; i < 4; i++) {
            choices[i] = words[i].getWord_target();                           // tao cac lua chon
        }
        setQuestion(words[correctChoice].getWord_explain());                      // tao cau hoi
    }

    public static void main(String[] args) {
        ChooseWordGame huy = new ChooseWordGame();
        huy.gameCommandline();
    }

}
