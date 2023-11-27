package game;

public class EliminateOneGameItem extends GameItem{

    /**
     * Constructor.
     */
    public EliminateOneGameItem() {
        name = "Eliminate";
    }

    /**
     * Give one incorrect choice.
     * @param game contains the choices and correct choice
     * @return an array with an incorrect choice
     */
    @Override
    public boolean[] used(MultipleChoiceGame game) {
        boolean[] eliminate = new boolean[4];
        for (int i = 0; i < 4; i += 1) {
            eliminate[i] = false;
        }

        if (isUsable()) {
            number -= 1;
            int first;

            if (game instanceof MemoryGame) {
                do {
                    first = (int) (Math.random() * 4);
                } while (first == ((MemoryGame) game).previousCorrectChoice);
            } else {
                do {
                    first = (int) (Math.random() * 4);
                } while (first == game.correctChoice);
            }

            eliminate[first] = true;
        }
        return eliminate;
    }

    @Override
    public String getInstruction() {
        return "   - " + name + " removes an incorrect choice. "
                + "You will get an additional use after 2 correct choices.\n";
    }
}
