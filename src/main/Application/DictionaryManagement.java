package Application;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class DictionaryManagement extends Dictionary {
    public void insertFromCommandline() {
       Scanner scan = new Scanner(System.in);
       System.out.print("Number of word added to the dictionary: ");
       int n = scan.nextInt();
       for (int i = 0; i < n; i++) {
           System.out.print("Word " + i + ": ");
           String word_target = scan.nextLine();
           System.out.print("\nExplain of word " + i + ": ");
           String word_explain = scan.nextLine();

           listWord.add(new Word(word_target, word_explain));
       }
    }

    public void insertFromFile() {
        try {
            File input = new File("/"); // Thêm path dictionaries.txt nheee
            Scanner scan = new Scanner(input);
            while (scan.hasNextLine()) {
                String[] word = scan.nextLine().split("\t");
                listWord.add(new Word(word[0], word[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}