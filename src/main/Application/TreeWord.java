package main.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TreeWord {
    private TreeNode root;

    public TreeWord() {
        root = new TreeNode();
    }

    public void addWord(Word w) {
        TreeNode newTree = new TreeNode();
        newTree = root;
        String word = w.getWord_target();
        for (int i = 0; i < word.length(); i++) {
            if (newTree == null) {
                newTree = new TreeNode();
            }
            if (!newTree.hasCharNext(word.charAt(i))) {
                newTree.insertNode(word.charAt(i));
            }

            if (i < word.length() - 1) newTree = newTree.getChild().get(word.charAt(i));
        }

        newTree.setCompleteWord(w);
    }

    public void addFromList(ArrayList<Word> listWord) {
        for (int i = 0; i < listWord.size(); i++) {
            addWord(listWord.get(i));
        }
    }

    private void traversingFromNode(TreeNode node, List<Word> listFound) {
        if (node.isCompleteWord()) {
            listFound.add(node.getCompleteWord());
            return;
        }

        for (Character c : node.getChild().keySet()) {
            traversingFromNode(node.getChild().get(c), listFound); // BackTracking
        }
    }

    public List<Word> search(String word) {
        TreeNode newTree = root;
        List<Word> listWord = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            if (newTree == null || !newTree.hasCharNext(word.charAt(i))) {
                return null;
            }
            if (i < word.length() - 1) newTree = newTree.getChild().get(word.charAt(i));
        }

        traversingFromNode(newTree, listWord);
        return listWord;
    }

    public static void main(String[] argv) {
        TreeWord test = new TreeWord();
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhap N");
        int n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Nhap chu thu " + i );
            String word = scan.next();
            test.addWord(new Word(word, ""));
        }

        String check = scan.next();
        ArrayList<Word> list = (ArrayList<Word>) test.search(check);

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getWord_target());
        }
    }
}
