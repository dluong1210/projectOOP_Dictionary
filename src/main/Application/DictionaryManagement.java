//package main.Application;
package Application;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Supports methods managing Dictionary.
 */
public class DictionaryManagement extends Dictionary {

    /**
     * Constructor (no parameter).
     */
    public DictionaryManagement() {
        super();
    }

    /**
     * Inserts Words from command-line into the Dictionary.
     */
    public void insertFromCommandline() {
       Scanner scan = new Scanner(System.in);
       System.out.print("Number of words added to the dictionary: ");
       int n = scan.nextInt();
       scan.nextLine(); // sua thanh nextLine de doc duoc ca dong chu next chi doc duoc 1 tu
       for (int i = 0; i < n; i++) {
           System.out.print("Word " + (i + 1) + ": ");
           String word_target = scan.nextLine();
           System.out.print("Explanation of word " + (i + 1) + ": ");
           String word_explain = scan.nextLine();

           listWord.addWord(new Word(word_target, word_explain));
       }
    }

    /**
     * Inserts Words from file dictionary.txt into the Dictionary.
     * <p> Each line in dictionary.txt contains
     * an English word with it's meaning and definition in Vietnamese separated by tab key. </p>
     */
    public void insertFromFile() {
        try {
            File input = new File("/"); // Thêm path dictionaries.txt nheee
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String[] word = scan.nextLine().split("\t");
                listWord.addWord(new Word(word[0], word[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Look up a word in the Dictionary using command-line.
     */
    public void dictionaryLookup() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhập từ cần tra cứu: ");
        String word = scan.nextLine();
        System.out.print(listWord.lookup(word));
    }

    /**
     * Add a word to the Dictionary using command-line.
     */
    public void dictionaryAdd() {
        // Ua co ham insertFromCommandline de lam quai gi?
        insertFromCommandline();
    }

    /**
     * Update a word in the Dictionary using command-line.
     */
    public void dictionaryUpdate() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhập từ cần sửa: ");
        String old_word_target = scan.nextLine();
        System.out.print("Nhập từ mới: ");
        String new_word_target = scan.nextLine();
        System.out.print("Nghĩa mới là: ");
        String new_word_explain = scan.nextLine();
        listWord.editWord(old_word_target, new_word_target, new_word_explain);
    }

    /**
     * Remove a word from the Dictionary using command-line.
     */
    public void dictionaryRemove() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhập từ cần xóa: ");
        String word_target = scan.nextLine();
        listWord.delete(word_target);
    }

    /**
     * Exports data stored in the Dictionary to a file.
     */
    public void dictionaryExportToFile() {
        System.out.print("Nhập tên file +(.txt): ");
        Scanner scan = new Scanner(System.in);
        String nameFile = scan.nextLine();
        try {
            File newFile = new File("src/" + nameFile + ".txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + nameFile + ".txt");
            } else {
                System.out.println("File already exists");
            }
            FileWriter fileWriter = new FileWriter("src/" + nameFile + ".txt");
            List<Word> listWordToShow = listWord.searchFrom("");
            for (Word word : listWordToShow) {
                fileWriter.write(word.getWord_target() + "\t" + word.getWord_explain() + "\n");
            }
            fileWriter.close();
            System.out.println("Successful Export!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imports data from a file into the Dictionary.
     */
    public void dictionaryImportFromFile() {
        // tu tu roi nghien cuu hihi na na insert voi export
        System.out.print("Nhập đường dẫn file: ");
        Scanner scanPath = new Scanner(System.in);
        String nameFile = scanPath.nextLine();
        try {
            File input = new File(nameFile);
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String[] word = scan.nextLine().split("\t");
                listWord.addWord(new Word(word[0], word[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}