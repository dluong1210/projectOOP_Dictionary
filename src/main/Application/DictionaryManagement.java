package main.Application;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class DictionaryManagement extends Dictionary {
    public DictionaryManagement() {
        super();
    }

    public void insertFromCommandline() {
       Scanner scan = new Scanner(System.in);
       System.out.print("Number of word added to the dictionary: ");
       int n = scan.nextInt();
       for (int i = 0; i < n; i++) {
           System.out.print("Word " + (i + 1) + ": ");
           String word_target = scan.next();
           System.out.print("Explain of word " + (i + 1) + ": ");
           String word_explain = scan.next();

           listWord.addWord(new Word(word_target, word_explain));
       }
    }

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

    public void dictionaryLookup() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nhập từ cần tra cứu: ");

    }
}