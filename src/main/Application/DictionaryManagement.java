package main.Application;
//package Application;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

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
    }

    /**
     * Update a word in the Dictionary using command-line.
     */
    public void dictionaryUpdate() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhap tu can sua mau: ");
        String word_target = scan.nextLine();
        System.out.print("Muon sua gi ha: ");
        String word_explain = scan.nextLine();
        //System.out.print(listWord.); chua co ham sua tu o TreeWord
    }

    /**
     * Remove a word from the Dictionary using command-line.
     */
    public void dictionaryRemove() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhap tu can xoa mau: ");
        String word_target = scan.nextLine();
        // chua co ham xoa tu o TreeWord
    }

    /**
     * Exports data stored in the Dictionary to a file.
     */
    public void dictionaryExportToFile() {
        // tu tu roi nghien cuu hihi
    }

    /**
     * Imports data from a file into the Dictionary.
     */
    public void dictionaryImportFromFile() {
        // tu tu roi nghien cuu hihi na na insert voi export
    }
}