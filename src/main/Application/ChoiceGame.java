package Application;

/**
 * Easy version.
 */
public abstract class ChoiceGame extends MultipleChoiceGame{
    /**
     * Four words must be distinct.
     */
    public void getValidWordChoices() {
        for (int i = 0; i < 4; i++) {
            words[i] = getRandomWord();
            boolean pickAgain = true;
            while (pickAgain) {
                words[i] = getRandomWord();
                pickAgain = false;                                   // tim tu thoa man rang buoc

                for (int j = 0; j < i; j += 1) {
                    if (words[i].equals(words[j])) {
                        pickAgain = true;
                        break;
                    }
                }
            }
        }
    }

    public void gameCommandline() {
        setPoints(0);
        dictionaryManagement.insertFromFile();                // tao database
        printRule();                                          // in luat
        startGame();                                          // bat dau luot choi moi
    }

    public void startGame() {
        setCorrectChoice((int) (Math.random() * 4));         // chon 1 lua chon se la dap an dung
        getValidWordChoices();                               // chon ra 4 tu lam de bai
        printQuestionAndChoices();                          // in ra man hinh
        setPlayerChoice(GameCommandline.getValidInput(option, "Your answer is: "));     // nhan input tu nguoi choi
        if (checkAnswer()) {
            startGame();                                    // bat dau luot choi moi
        } else {
            endGame();                                      // bao diem ket thuc game
        }
    }

}
