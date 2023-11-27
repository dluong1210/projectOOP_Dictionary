package application;

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

    public void initGame() {
        dictionaryManagement.insertFromFile();                                  // tao database
        int random = (int) (Math.random() * 4);
        setCorrectChoice(random);                                               // chon 1 lua chon se la dap an dung

        for (int i = 0; i < 4; i++) {
            Word choiceWord = getRandomWord();
            boolean pickAgain = true;

            while (pickAgain) {
                choiceWord = getRandomWord();
                pickAgain = false;                                   // tim tu thoa man rang buoc

                for (int j = 0; j < i; j += 1) {
                    if (choiceWord.getWord_target().equals(choices[j])) {
                        pickAgain = true;
                        break;
                    }
                }
            }

            choices[i] = choiceWord.getWord_target();                           // tao cac lua chon
            if (i == correctChoice) {
                setQuestion(choiceWord.getWord_explain());                      // tao cau hoi
            }
        }
    }

    public static void main(String[] args) {
        ChooseWordGame huy = new ChooseWordGame();
        huy.gameCommandline();
    }
}
