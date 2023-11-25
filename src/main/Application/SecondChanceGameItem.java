package Application;

public class SecondChanceGameItem extends GameItem{
    /**
     * Is this item being used.
     */
    private boolean isInvincible = false;

    /**
     * Give one incorrect choice.
     * @param game contains the choices and correct choice
     * @return an array with an incorrect choice from the player
     */
    @Override
    public boolean[] used(MultipleChoiceGame game) {
        boolean[] eliminate = new boolean[4];
        for (int i = 0; i < 4; i += 1) {
            eliminate[i] = false;
        }

        if (isUsable() && isInvincible) {
            number -= 1;
            for (int i = 0; i < 4; i += 1) {
                if (game.playerChoice.equals(game.option[i])) {
                    eliminate[i] = true;
                }
            }
        }

        isInvincible = true;
        return eliminate;
    }

//////// Getter / setter

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
}
