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

        if (listWordToShow == null || listWordToShow.isEmpty()) {
            System.out.println("Empty !!!");
            return;
        }

        for (int i = 0; i < listWordToShow.size(); ++i) {
            Word word = listWordToShow.get(i);
            System.out.printf("%-20s | %-20s | %-20s\n", i + 1, word.getWord_target(), word.getWord_explain());
        }
    }

    /**
     * Rat la met hic.
     */
    public void dictionaryAdvanced() {
        boolean quit = false;
        String choice;
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to My Application!");
        while (true) {
            System.out.println("MENU:");
            System.out.println("[0] Exit");
            System.out.println("[1] Add");
            System.out.println("[2] Remove");
            System.out.println("[3] Update");
            System.out.println("[4] Display");
            System.out.println("[5] Lookup");
            System.out.println("[6] Search");
            System.out.println("[7] Game (Random Word)");
            System.out.println("[8] Import from file");
            System.out.println("[9] Export to file");
            System.out.println("Your action: ");
            int action = scan.nextInt();
            switch (action) {
                case 0:
                    quit = true;
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
//                    for (int i = 0; i < 25; i++) {
                        Word test = listWord.getRandomWord(4, 10);
                        System.out.println(test.getWord_target() + " ---> " + test.getWord_explain());
//                    }
                    break;
                case 8:
//                    dictionaryImportFromFile();
                    insertFromFile();
                    break;
                case 9:
                    dictionaryExportToFile();
                    break;
                default:
                    System.out.println("Action not supported"); // nen de xu li la String khong phai int, sua sau
                    break;
            }
            if (quit) break;
            while (true) {
                System.out.println("Do you want continue ?:"    // nen them using dictionary
                                    + "\n1.Yes\t2.No");
                choice = scan.next();
                if (!choice.equals("1") && !choice.equals("2")) {
                    System.out.println("Chose 1 of 2 !!");
                    continue;
                }
                break;
            }
            if (choice.equals("2")) break;
        }
        System.out.println("Byeee");
    }

    public static void main(String[] argv) {
        DictionaryCommandline test = new DictionaryCommandline();
        test.dictionaryAdvanced();
    }
}
