//package main.Application;
package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
       String choice;
       Scanner scan = new Scanner(System.in);
       ArrayList<String> target = new ArrayList<String>();
       ArrayList<String> explain = new ArrayList<String>();
       while (true) {
           target.clear();
           explain.clear();
           System.out.print("Number of words added to the dictionary: ");
           int n = scan.nextInt();
           scan.nextLine(); // sua thanh nextLine de doc duoc ca dong chu next chi doc duoc 1 tu
           for (int i = 0; i < n; i++) {
               System.out.print("Word " + (i + 1) + ": ");
               String word_target = scan.nextLine();
               target.add(word_target);
               System.out.print("Explanation of word " + (i + 1) + ": ");
               String word_explain = scan.nextLine();
               explain.add(word_explain);

           }
           boolean repeat = false;
           while (true) {
               System.out.printf("%-20s | %-20s | %-20s\n", "No", "Word", "Explain");
               for (int i = 0; i < n; i++) {
                   //Word word = listWordToShow.get(i);
                   System.out.printf("%-20s | %-20s | %-20s\n", i + 1, target.get(i), explain.get(i));
               }
               System.out.println("Are you sure!" + "\n1.Yes\t2.No\t3.Exit");
               if (repeat) System.out.println("Choose one of three !!");
               choice = scan.next();
               if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                   repeat = true;
                   continue;
               } else repeat = false;
               if (choice.equals("1") || choice.equals("3")) break;
               if (choice.equals("2")) {
                   System.out.println("Do you want to correct any word?"
                           + "\n1.Yes\t2.No");
                   String choose = scan.next();
                   if (choose.equals("2")) return;
                   System.out.println("The numbers of the words to be corrected are:");
                   scan.nextLine();
                   String s = scan.nextLine();
                   int i=0;
                   int number = 0;
                   while (i<s.length())
                   {
                       if (Character.isDigit(s.charAt(i)))
                       {
                           number = 0;
                           while (Character.isDigit(s.charAt(i)))
                           {
                               number = number * 10 + Character.getNumericValue(s.charAt(i));
                               i++;
                               if (i >= s.length()) break;
                           }
                           while (number > target.size()) {
                               System.out.println("Word[" + number + "] doesn't exist");
                               System.out.print("New number: ");
                               number = scan.nextInt();
                               scan.nextLine();
                           }
                           System.out.print("Word " + number + ": ");
                           String word_target = scan.nextLine();
                           target.set(number - 1,word_target);
                           System.out.print("Explanation of word " + number + ": ");
                           String word_explain = scan.nextLine();
                           explain.set(number - 1,word_explain);
                           i--;
                       }
                       i++;
                   }
                   continue;
               }
           }
           if (choice.equals("3")) break;

           //kiem tra xem cac tu trong list da xuat hien chưa
           ArrayList<Word> checkword = new ArrayList<Word>();
           for (int i = 0; i < n; i++)  {
               Word word = new Word();
               word = listWord.lookup(target.get(i));
               if (word != null)
                   checkword.add(word);
           }
           if (!checkword.isEmpty()) {
               for (int i = 0; i < checkword.size(); i++)
                   System.out.println(checkword.get(i).getWord_target());
               System.out.println("They exist in dictionary");
               while (true) {
                   System.out.println("Do you want to update them ?:"
                           + "\n1.Yes\t2.No");
                   choice = scan.next();
                   if (!choice.equals("1") && !choice.equals("2")) {
                       System.out.println("Choose 1 or 2 !!");
                       continue;
                   }
                   break;
               }
               //add cac tu còn lại
               if (choice.equals("2")) {
                   int index = 0;
                   String u;
                   String v = new String();
                   for (int i = 0; i < n; i++) {
                       u = target.get(i);
                       if (index < checkword.size())
                           v = checkword.get(index).getWord_target();
                       if (u.equals(v)) index++;
                           else listWord.addWord(new Word(u, explain.get(i)));
                   }
                   return;
               }

           }
           for (int i = 0; i < n; i++)
               listWord.addWord(new Word(target.get(i), explain.get(i)));
           return;
       }
    }

    /**
     * Inserts Words from file dictionary.txt into the Dictionary.
     * <p> Each line in dictionary.txt contains
     * an English word with it's meaning and definition in Vietnamese separated by tab key. </p>
     */
    public void insertFromFile() {
        try {
            File input = new File("src/resources/data/dictionary.txt"); // Thêm path dictionary.txt nheee
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
        String word;
        while (true) {
            System.out.print("Enter your word: ");
            word = scan.nextLine();
            if (!word.isEmpty()) break;
            System.out.println("Error: No information!");
        }
        if (listWord.lookup(word) == null) {
            System.out.println("Word not exist in dictionary");
            String choice;
            while (true) {
                System.out.println("Do you want to add this word ?:"
                        + "\n1.Yes\t2.No");
                choice = scan.next();
                if (!choice.equals("1") && !choice.equals("2")) {
                    System.out.println("Choose 1 or 2 !!");
                    continue;
                }
                break;
            }
            if (choice.equals("2")) return;
            System.out.print("Explanation of word : ");
            String word_explain = scan.next();
            listWord.addWord(new Word(word,word_explain));
        }
        else System.out.println(listWord.lookup(word).getWord_explain());
//        System.out.println(MySQL.selectFromDB(word));
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
        System.out.print("Enter your word: ");
        String old_word_target = scan.nextLine();
        if (listWord.lookup(old_word_target) == null) {
            System.out.println("Word not exist in dictionary");
            String choice;
            while (true) {
                System.out.println("Do you want to add this word ?:"
                        + "\n1.Yes\t2.No");
                choice = scan.next();
                if (!choice.equals("1") && !choice.equals("2")) {
                    System.out.println("Choose 1 or 2 !!");
                    continue;
                }
                break;
            }
            if (choice.equals("2")) return;
            System.out.print("Explanation of word : ");
            String word_explain = scan.next();
            listWord.addWord(new Word(old_word_target, word_explain));
            return;
        }
        System.out.print("New word: ");
        String new_word_target = scan.nextLine();
        System.out.print("New explain: ");
        String new_word_explain = scan.nextLine();
        listWord.editWord(old_word_target, new_word_target, new_word_explain);
    }

    /**
     * Remove a word from the Dictionary using command-line.
     */
    public void dictionaryRemove() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the word you want to remove: ");
        String word_target = scan.nextLine();
        listWord.delete(word_target);
    }

    /**
     * Exports data stored in the Dictionary to a file.
     */
    public void dictionaryExportToFile() {
        System.out.print("Enter file + (.txt): ");
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
        System.out.print("Enter path: ");
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