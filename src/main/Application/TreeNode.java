//package main.Application;
package Application;

import java.util.Map;
import java.util.HashMap;

public class TreeNode {
    private Map<Character, TreeNode> child;
    private Word completeWord;

    public TreeNode() {
        child = new HashMap<>(30, 1);
    }

    public Map<Character, TreeNode> getChild() {
        return child;
    }

    public boolean deleteChild() {
        for (Character check : child.keySet()) {
            if (child.get(check) != null) return false;
        }
        child = null;
        return true;
    }

    public void setCompleteWord(Word word) {
        completeWord = word;
    }

    public Word getCompleteWord() {
        return completeWord;
    }

    public boolean isCompleteWord() {
        return completeWord != null;
    }

    public boolean isLastChar() {
        return child == null || child.isEmpty();
    }

    public boolean hasCharNext(Character check) {
        return !isLastChar() && child.containsKey(check);
    }

    public void insertNode(Character c) {
        if (isLastChar()) {
            new TreeNode();
        }
        child.put(c, new TreeNode());
    }
}