package Application;

public class ChooseMeaningGame extends GameCommandLine{

    public String giveQuestion() {
        return "Question " + (points + 1) + ": \nWhich is the meaning of the following word?: " + question;
    }

    public Word getRandomWord() {
        return dictionaryManagement.listWord.getRandomWord(1,1);                    // tra ve Word random lay trong database
    }

    @Override
    public void setChoicesAndQuestion(Word[] words) {
        for (int i = 0; i < 4; i++) {
            choices[i] = words[i].getWord_explain();                           // tao cac lua chon
        }
        setQuestion(words[correctChoice].getWord_explain());                      // tao cau hoi
    }

    public static void main(String[] args) {
        ChooseMeaningGame huy = new ChooseMeaningGame();
        huy.gameCommandline();
    }

}
