package main.Application;
//package Application;

/**
 * Contains an English word with it's meaning and definition in Vietnamese.
 */
public class Word {
    /**
     * English word.
     */
    private String word_target;
    /**
     * Vietnamese meaning and definition.
     */
    private String word_explain;

    /**
     * Initializes a newly created Word object.
     * @param word_target English word
     * @param word_explain Vietnamese meaning and definition
     */
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    /**
     * Gets the English word.
     * @return word_target - English word
     */
    public String getWord_target() {
        return word_target;
    }

    /**
     * Sets the English word.
     * @param word_target English word
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**
     * Gets the Vietnamese meaning and definition.
     * @return word_explain - Vietnamese meaning and definition
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * Sets the Vietnamese meaning and definition.
     * @param word_explain Vietnamese meaning and definition
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
}