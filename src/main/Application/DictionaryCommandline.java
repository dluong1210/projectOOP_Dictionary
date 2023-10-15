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

    /**
     * Find all words start with given string.
     */
    public void dictionarySearcher() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Find words start with: ");
        String wordStart = scan.nextLine();
        List<Word> listWordToShow = listWord.searchFrom(wordStart);

        for (int i = 0; i < listWordToShow.size(); ++i) {
            Word word = listWordToShow.get(i);
            System.out.printf("%-20s | %-20s | %-20s\n", i + 1, word.getWord_target(), word.getWord_explain());
        }
    }

    /**
     * Rat la met hic.
     */
    public void dictionaryAdvanced() {
        System.out.println("Welcome to My Application!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import from file");
        System.out.println("[9] Export to file");
        System.out.println("Your action: ");
        Scanner scan = new Scanner(System.in);
        int action = scan.nextInt();
        switch (action) {
            case 0:
                break;
            case 1:
                dictionaryAdd();
                break;
            case 2:
                dictionaryRemove();
                break;
            case 3:
                dictionaryUpdate();
                break;
            case 4:
                showAllWords();
                break;
            case 5:
                dictionaryLookup();
                break;
            case 6:
                dictionarySearcher();
                break;
            case 7:
                break;
            case 8:
                dictionaryImportFromFile();
                break;
            case 9:
                dictionaryExportToFile();
                break;
            default:
                System.out.println("Action not supported");
                break;
        }
    }

    public static void main(String[] argv) {
        DictionaryCommandline test = new DictionaryCommandline();
        test.dictionaryAdvanced();
    }
}
