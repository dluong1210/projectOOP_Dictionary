package Application;

import java.util.Map;
import java.util.HashMap;

public class TreeNode {
    private Map<Character, TreeNode> child;
    private Word completeWord;

    public TreeNode() {
        child = new HashMap<>(27, 1);
    }

    public Map<Character, TreeNode> getChild() {
        return child;
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
        return child == null;
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