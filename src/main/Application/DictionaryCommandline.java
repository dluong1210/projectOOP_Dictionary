//package main.Application;
package Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    public DictionaryCommandline() {
        super();
    }

    public void showAllWords() {
        System.out.printf("%-20s | %-20s | %-20s\n", "No", "English", "Vietnamese");
        List<Word> listWordToShow = listWord.searchFrom("");

        for (int i = 0; i < listWordToShow.size(); ++i) {
            Word word = listWordToShow.get(i);
            System.out.printf("%-20s | %-20s | %-20s\n", i + 1, word.getWord_target(), word.getWord_explain());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    public void dictionarySearcher() {

    }

    public static void main(String[] argv) {
        DictionaryCommandline test = new DictionaryCommandline();
        test.dictionaryBasic();
    }
}
