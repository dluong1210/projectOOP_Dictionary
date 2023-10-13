//package main.Application;
package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TreeWord {
    final private TreeNode root;

    public TreeWord() {
        root = new TreeNode();
    }

    final public TreeNode getRoot() {
        return root;
    }

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

    public void addFromList(ArrayList<Word> listWord) {
        for (Word word : listWord) {
            addWord(word);
        }
    }

    public boolean editWord(String currentWord_target, String newWord_target, String newWord_explain) {
        Word wordEdit = lookup(currentWord_target);
        if (wordEdit == null) {
            System.out.println(currentWord_target + " not exist in dictionary");
            return false;
        }

        wordEdit.setWord_target(newWord_target);
        wordEdit.setWord_explain(newWord_explain);

        return delete(currentWord_target) && addWord(wordEdit);
    }

    public boolean delete(String word) {
        if (lookup(word) == null) {
            System.out.println(word + " not exist");
            return false;
        }

        deleteBranch(root, word, 0);
        return true;
    }

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



    private void traversingFromNode(TreeNode node, List<Word> listFound) {
        if (node.isCompleteWord()) {
            listFound.add(node.getCompleteWord());
        }

        if (node.isLastChar()) return;
        for (Character c : node.getChild().keySet()) {
            traversingFromNode(node.getChild().get(c), listFound); // BackTracking
        }
    }

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
        test.editWord("hello", "hi", "xinchao");
//        String check = scan.next();
//        System.out.println(test.delete(check));
//        test.lookup(check);
//        System.out.println(test.lookup(check).getWord_explain());
        ArrayList<Word> list = (ArrayList<Word>) test.searchFrom("");
//        test.traversingFromNode(test.root, list);
        for (Word word : list) {
            System.out.println(word.getWord_target());
        }
    }
}
