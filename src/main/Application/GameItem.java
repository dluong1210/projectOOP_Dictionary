package Application;

public abstract class GameItem {
    /**
     * Number of times can be used left.
     */
    protected int number;

    /**
     * Use item to know some incorrect choices.
     * @param game contains the choices and correct choice
     * @return an array of incorrect choice given by the item
     */
    public abstract boolean[] used(MultipleChoiceGame game);

    /**
     * Can the item still be used.
     * @return true if number of times can be used left haven't reached 0
     */
    public boolean isUsable() {
        return number > 0;
    }


//////// Getter / setter

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
