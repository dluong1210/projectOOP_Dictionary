package Application;

/**
 * Easy version.
 */
public abstract class ChoiceGame extends MultipleChoiceGame{

    @Override
    public void initGame() {
        setPoints(0);
        dictionaryManagement.insertFromFile();                // tao database
    }

    @Override
    public void getPrepared() {
        setCorrectChoice((int) (Math.random() * 4));         // chon 1 lua chon se la dap an dung
        getValidWordChoices();                               // chon ra 4 tu lam de bai
    }

    /**
     * Four words must be distinct.
     */
    @Override
    public void getValidWordChoices() {
        for (int i = 0; i < 4; i++) {
            words[i] = getRandomWord();
            boolean pickAgain = true;
            while (pickAgain) {
                words[i] = getRandomWord();
                pickAgain = false;                       // tim tu thoa man rang buoc

                for (int j = 0; j < i; j += 1) {
                    if (words[i].equals(words[j])) {
                        pickAgain = true;
                        break;
                    }
                }
            }
        }
    }
}
