package Application;

/**
 * Hard game using memory.
 */
public abstract class MemoryGame extends MultipleChoiceGame{
    /**
     * The word going to appear in the next turn.
     */
    protected Word nextWord;
    /**
     * Four words generated from the previous turn.
     */
    protected Word[] previousWordChoices = new Word[4];
    /**
     * The correct option generated from the previous turn.
     */
    protected int previousCorrectChoice = -1;

    /**
     * Four words must be distinct and different from the nextWord.
     * Then overwritten the correct word by the nextWord.
     * Update the nextWord.
     */
    public void getValidWordChoices() {
        for (int i = 0; i < 4; i++) {
            words[i] = getRandomWord();
            boolean pickAgain = true;

            while (pickAgain) {
                words[i] = getRandomWord();
                pickAgain = false;                                   // tim tu thoa man rang buoc
                if (points > 0) {
                    pickAgain = words[i].equals(nextWord);
                }
                for (int j = 0; j < i; j += 1) {
                    if (words[i].equals(words[j])) {
                        pickAgain = true;
                        break;
                    }
                }
            }
        }

        if (points > 0) {
            words[correctChoice] = nextWord;
        }

        nextWord = words[(int) (Math.random() * 4)];
    }

    /**
     * Update the previous words, swap the previous correct options and the current correct options.
     */
    public void copyChoices() {
        for (int i = 0; i < 4; i += 1) {
            previousWordChoices[i] = words[i];
        }
        int aux = previousCorrectChoice;
        previousCorrectChoice = correctChoice;
        correctChoice = aux;
    }


    public void gameCommandline() {
        setPoints(0);
        dictionaryManagement.insertFromFile();                          // tao database
        setCorrectChoice((int) (Math.random() * 4));                    // chon 1 lua chon se la dap an dung
        getValidWordChoices();                                          // chon ra 4 tu lam de bai
        printRule();                                                    // in luat
        copyChoices();                                                  // luu lai
        startGame();
    }

    public void startGame() {
        setCorrectChoice((int) (Math.random() * 4));                                                // chon 1 lua chon se la dap an dung
        getValidWordChoices();                                                                      // chon ra 4 tu lam de bai
        printQuestionAndChoices();                                                                  // in ra man hinh
        setPlayerChoice(GameCommandline.getValidInput(option, "Your answer is: "));  // nhan input tu nguoi choi
        copyChoices();                                                                              // luu lai
        if (checkAnswer()) {
            startGame();                                                                            // bat dau luot choi moi
        } else {
            endGame();                                                                              // bao diem ket thuc game
        }
    }

//////// Getter / setter

    public Word getNextWord() {
        return nextWord;
    }

    public void setNextWord(Word nextWord) {
        this.nextWord = nextWord;
    }

    public Word[] getPreviousWordChoices() {
        return previousWordChoices;
    }

    public void setPreviousWordChoices(Word[] previousWordChoices) {
        this.previousWordChoices = previousWordChoices;
    }

    public int getPreviousCorrectChoice() {
        return previousCorrectChoice;
    }

    public void setPreviousCorrectChoice(int previousCorrectChoice) {
        this.previousCorrectChoice = previousCorrectChoice;
    }

//// Old method.


    /**
     * Copy words from another array to this array.
     * @param paste this array
     * @param copy the another array
     */
    /*
    public void copyChoices(Word[] paste, Word[] copy) {
        for (int i = 0; i < 4; i += 1) {
            paste[i] = copy[i];
        }
    }
*/

    /**
     * Switch the previous words and the current words.
     */
    /*
    public void switchChoices() {
        Word[] auxChoices = new Word[4];
        int auxCorrectChoice;

        copyChoices(auxChoices, previousWordChoices);
        auxCorrectChoice = previousCorrectChoice;

        copyChoices(previousWordChoices, words);
        previousCorrectChoice = correctChoice;

        copyChoices(words, auxChoices);
        correctChoice = auxCorrectChoice;
    }


     */

}
