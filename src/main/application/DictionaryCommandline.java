//package main.Application;
package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    private ArrayList<String> historic = new ArrayList<String>();

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

    public void showHistoric() {
        for (int i = 0; i < historic.size() - 1; i++) {
            System.out.print(historic.get(i));
        }
        String u = historic.get(historic.size() - 1);
        for (int i = 0; i < u.length(); i++)
        {
            if (u.charAt(i) == '-') return ;
            System.out.print(u.charAt(i));
        }
    }

    /**
     * Rat la met hic.
     */
    public void dictionaryAdvanced() {
        boolean quit = false;
       // boolean question_continue = true;
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
            System.out.println("[7] Game");
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
                    historic.add("Add->");
                    break;
                case 2:
                    dictionaryRemove();
                    historic.add("Remove->");
                    break;
                case 3:
                    dictionaryUpdate();
                    historic.add("Update->");
                    break;
                case 4:
                    showAllWords();
                    historic.add("Display->");
                    break;
                case 5:
                    dictionaryLookup();
                    historic.add("Lookup->");
                    break;
                case 6:
                    dictionarySearcher();
                    historic.add("Search->");
                    break;
                case 7:
                    historic.add("Game->");
                    break;
                case 8:
    //                dictionaryImportFromFile();
                    insertFromFile();
                    historic.add("Import from file->");
                    break;
                case 9:
                    dictionaryExportToFile();
                    historic.add("Export to file->");
                    break;
                default:
                    System.out.println("Action not supported"); // nen de xu li la String khong phai int, sua sau
                    break;
            }
            if (quit) break;
            while (true) {
                System.out.println("Do you want to continue using my app ?:"    // nen them using dictionary
                                    + "\n1.Yes\t2.No");
                choice = scan.next();
                if (!choice.equals("1") && !choice.equals("2")) {
                    System.out.println("Choose 1 or 2 !!");
                    continue;
                }
                clearScreen();
                break;
            }
            if (choice.equals("2")) break;
        }
        System.out.println("Byeee");
    }

    public final static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] argv) {
        DictionaryCommandline test = new DictionaryCommandline();
        test.dictionaryAdvanced();
       // test.showHistoric();
    }
}
