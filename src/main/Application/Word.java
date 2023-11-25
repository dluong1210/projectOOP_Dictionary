//package main.Application;
package Application;

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

    public Word() {

    }

    public Word(Word other) {
        this.word_explain = other.getWord_explain();
        this.word_target = other.getWord_target();
    }

    /**
     * Compares this word to the specified object.  The result is {@code
     * true} if and only if the argument is not {@code null} and is a {@code
     * Word} object that represents the same word_target and word_explain as this object.
     *
     * @param  obj
     *         The object to compare this {@code Word} against
     *
     * @return  {@code true} if the given object represents a {@code Word}
     *          equivalent to this word, {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Word anotherWord) {
            return this.word_target.equals(anotherWord.word_target) && this.word_explain.equals(anotherWord.word_explain);
        }
        return false;
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