package application;


/**
 * Stores a list of Word objects.
 */
public class Dictionary {
    /**
     * A trie.
     */
    protected TreeWord listWord;

    /**
     * Constructor (no parameter).
     */
    public Dictionary() {
        listWord = new TreeWord();
    }

}