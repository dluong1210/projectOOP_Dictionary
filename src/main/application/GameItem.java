package application;

public abstract class GameItem {
    /**
     * Number of times can be used left.
     */
    protected int number = 0;

    /**
     * Item's name.
     */
    protected String name;

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

    /**
     * Increase one more time to use item.
     * @param point the point player has just earned
     * @param time frequency of adding
     */
    public void increaseUsage(int point, int time) {
        if (point % time == 0) {
            number += 1;
        }
    }

    public String getText() {
        return name + " [" + number + "]";
    }

    /**
     * How to use this item?
     * @return thing this item can do
     */
    public abstract String getInstruction();

//////// Getter / setter

//    public int getNumber() {
//        return number;
//    }

    public void setNumber(int number) {
        this.number = number;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
}
