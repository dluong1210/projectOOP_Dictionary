package application;

public class SecondChanceGameItem extends GameItem{
    /**
     * Is this item being used.
     */
    private boolean isInvincible = false;

    /**
     * Constructor.
     */
    public SecondChanceGameItem() {
        name = "Invincible";
    }

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

        // Su dung
        if (isInvincible) {
            for (int i = 0; i < 4; i += 1) {
                if (game.playerChoice.equals(game.option[i])) {
                    eliminate[i] = true;
                }
            }
            isInvincible = false;
        } else {
            // Bat dau su dung
            if (isUsable()) {
                number -= 1;
                isInvincible = true;
            }
        }
        return eliminate;
    }

    @Override
    public String getInstruction() {
        return "   - " + name + " allows to choose another choice for the second time in case the first choice is incorrect. "
                + "You will get an additional use after 4 correct choices.\n";
    }

    //////// Getter / setter

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
}
