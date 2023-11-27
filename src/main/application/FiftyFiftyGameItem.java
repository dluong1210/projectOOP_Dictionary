package application;

public class FiftyFiftyGameItem extends GameItem{

    public FiftyFiftyGameItem() {
        name = "Fifty-Fifty";
    }

    /**
     * Give two incorrect choices.
     * @param game contains the choices and correct choice
     * @return an array with two incorrect choices
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
            int second;

            if (game instanceof MemoryGame) {
                do {
                    first = (int) (Math.random() * 4);
                } while (first == ((MemoryGame) game).previousCorrectChoice);

                do {
                    second = (int) (Math.random() * 4);
                } while (second == ((MemoryGame) game).previousCorrectChoice || second == first);
            } else {
                do {
                    first = (int) (Math.random() * 4);
                } while (first == game.correctChoice);

                do {
                    second = (int) (Math.random() * 4);
                } while (second == game.correctChoice || second == first);
            }

            eliminate[first] = true;
            eliminate[second] = true;
        }

        return eliminate;
    }

    @Override
    public String getInstruction() {
        return "   - " + name + " removes half of the wrong choice. "
                + "You will get an additional use after 6 correct choices.\n";
    }
}
