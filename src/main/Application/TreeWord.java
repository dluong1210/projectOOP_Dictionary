//package main.Application;
package Application;

import java.util.*;

/**
 * The `TreeWord` class represents a tree-based dictionary.
 */
public class TreeWord {
    final private TreeNode root;

    /**
     * Constructor: Initializes a new tree-based dictionary.
     */
    public TreeWord() {
        root = new TreeNode();
    }

    /**
     * Returns the root node of the tree.
     *
     * @return The root node.
     */
    final public TreeNode getRoot() {
        return root;
    }

    /**
     * Adds a word to the dictionary tree.
     *
     * @param w Word to be added.
     * @return true if added successfully.
     */
    public boolean addWord(Word w) {
        TreeNode newTree = root;
        String word = w.getWord_target();
        for (int i = 0; i < word.length(); i++) {
            if (!newTree.hasCharNext(word.charAt(i))) {
                newTree.insertNode(word.charAt(i));
            }

            newTree = newTree.getChild().get(word.charAt(i));
        }

        newTree.setCompleteWord(w);
        return true;
    }

    /**
     * Adds a list of words to the dictionary tree.
     *
     * @param listWord List of Word objects to be added.
     */
    public void addFromList(ArrayList<Word> listWord) {
        for (Word word : listWord) {
            addWord(word);
        }
    }

    /**
     * Deletes a word from the dictionary.
     *
     * @param word The word to delete.
     * @return true if deleted successfully, false if the word does not exist in the dictionary.
     */
    public boolean delete(String word) {
        if (lookup(word) == null) {
            System.out.println(word + "does not exist in dictionary");
            return false;
        }

        deleteBranch(root, word, 0);
        return true;
    }

    /**
     * Recursively deletes a branch of nodes corresponding to a word in the tree.
     * Use for method 'delete'.
     *
     * @param currentNode  The current node in the traversal.
     * @param word         The word to delete.
     * @param currentIndex The current index in the word being processed.
     */
    public void deleteBranch(TreeNode currentNode, String word, int currentIndex) {
        if (currentIndex == word.length()) {
            currentNode.setCompleteWord(null);
        }

        else if (currentNode.hasCharNext(word.charAt(currentIndex))) {
            deleteBranch(currentNode.getChild().get(word.charAt(currentIndex)), word, currentIndex + 1);
        }

        if ((currentNode.isLastChar() || currentNode.deleteChild()) && currentNode.getCompleteWord() == null) {
            currentNode = null;
        }
    }

    /**
     * Edits a word in the dictionary.
     *
     * @param currentWord_target The word to edit.
     * @param newWord_target     The new word.
     * @param newWord_explain    The new word explanation.
     * @return true if the edit was successful, false if the word does not exist in the dictionary.
     */
    public boolean editWord(String currentWord_target, String newWord_target, String newWord_explain) {
        Word wordEdit = lookup(currentWord_target);
        if (wordEdit == null) {
            System.out.println(currentWord_target + " does not exist in dictionary");
            return false;
        }

        wordEdit.setWord_target(newWord_target);
        wordEdit.setWord_explain(newWord_explain);

        return delete(currentWord_target) && addWord(wordEdit);
    }

    /**
     * Searches for words in the dictionary that start with the specified word.
     *
     * @param word The word to start the search from.
     * @return A list of words that start by 'word'.
     */
    public List<Word> searchFrom(String word) {
        TreeNode newTree = root;
        List<Word> listWord = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (newTree == null || !newTree.hasCharNext(word.charAt(i))) {
                return null;
            }

            newTree = newTree.getChild().get(word.charAt(i));
        }

        traversingFromNode(newTree, listWord);
        return listWord;
    }

    /**
     * Recursively traverses the tree from a specific node and adds complete words to a list.
     * Use for method 'searchFrom'.
     *
     * @param node      The current node in the traversal.
     * @param listFound The list to store found words.
     */
    private void traversingFromNode(TreeNode node, List<Word> listFound) {
        if (node.isCompleteWord()) {
            listFound.add(node.getCompleteWord());
        }

        if (node.isLastChar()) return;
        for (Character c : node.getChild().keySet()) {
            traversingFromNode(node.getChild().get(c), listFound); // BackTracking
        }
    }

    /**
     * Looks up a word in the dictionary.
     *
     * @param word The word to look up.
     * @return The Word object if found, or null if the word is not in the dictionary.
     */
    public Word lookup(String word) {
        TreeNode newTree = root;
        for (int i = 0; i < word.length(); i++) {
            if (newTree == null || !newTree.hasCharNext(word.charAt(i))) {
                return null;
            }

            newTree = newTree.getChild().get(word.charAt(i));
        }
        return newTree.getCompleteWord();
    }

    /** return a random word with 2 param. */
    public Word getRandomWord(int minLength, int maxLength) {
        Random rand = new Random();
        TreeNode newNode = root;
        int count = 0;

        while (true) {
            int random = rand.nextInt(newNode.getChild().size() + 1);
            for (Map.Entry<Character, TreeNode> entry : newNode.getChild().entrySet()) {
                if (random <= 1) {
                    newNode = entry.getValue();
                    break;
                }
                random--;
            }
            count++;

            if (newNode.isLastChar() || newNode.isCompleteWord()) break;
        }

        if (count < minLength || count > maxLength) return getRandomWord(minLength, maxLength);
        return newNode.getCompleteWord();
    }

    /**
     * Main method for testing the TreeWord class.
     */
    public static void main(String[] argv) {
        TreeWord test = new TreeWord();
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập N");
        int n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhap chu thu " + i );
            String word = scan.next();
            test.addWord(new Word(word, "abc"));
        }
//        test.editWord("hello", "hi", "xinchao");
//        String check = scan.next();
//        System.out.println(test.delete(check));
//        test.lookup(check);
//        System.out.println(test.lookup(check).getWord_explain());
//        ArrayList<Word> list = (ArrayList<Word>) test.searchFrom("");
//        test.traversingFromNode(test.root, list);
//        for (Word word : list) {
//            System.out.println(word.getWord_target());
//        }
//        for (int i = 0; i < 25; i++) {
//            System.out.println(test.getRandomWord().getWord_target());
//        }
    }
}
