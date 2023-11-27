package application;

/**
 * Game chon dap an dung.
 */
public abstract class MultipleChoiceGame {

    protected DictionaryManagement dictionaryManagement = new DictionaryManagement();
    protected static final String[] option = {"A", "B", "C", "D"};
    protected String question;
    protected String[] choices = new String[4];
    protected int playerChoice;
    protected int correctChoice;
    protected int points;

    public String endGame() {
        return "Congratulation! Your points is: " + points;
    }

    public abstract String giveQuestion() ;

    public String giveChoice(int i) {
        return option[i] + ". " + choices[i];
    }

    public void setPlayerAnswer(String inputChoice) {
        for (int i = 0; i < 4; i += 1) {
            if (inputChoice.equals(option[i])) {
                setPlayerChoice(i);
            }
        }
    }

    public abstract Word getRandomWord();
    public abstract void initGame();












































    // Getter / setter

    public DictionaryManagement getDictionaryManagement() {
        return dictionaryManagement;
    }

    public void setDictionaryManagement(DictionaryManagement dictionaryManagement) {
        this.dictionaryManagement = dictionaryManagement;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(int playerChoice) {
        this.playerChoice = playerChoice;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



}
