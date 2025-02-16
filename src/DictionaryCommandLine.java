import javax.swing.*;
import java.util.Scanner;
import java.util.Vector;

public class DictionaryCommandLine {
    private DictionaryManagement dictionaryManagement = new DictionaryManagement();

    public void showAllWords() {
        int size = dictionaryManagement.dictionary.getCurrentSize();

        System.out.println("No      |English        |Vietnamese");

        for (int i = 0; i < size ; i++) {
            System.out.println((i+1) + "        |" + dictionaryManagement.dictionary.getDictAtElement(i).getWordTarget()
            + "        |" + dictionaryManagement.dictionary.getDictAtElement(i).getWordExplain());
        }
    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandLine();
        showAllWords();
    }

    public void helpCommand() {
        System.out.println("Type '-help' to show all commands.");
        System.out.println("Type '-end' to end the program.");
        System.out.println("Type '-lookup' to look up a word's meaning.");
        System.out.println("Type '-show' to display the entire dictionary.");
        System.out.println("Type '-add' to add an entry.");
        System.out.println("Type '-delete' to delete an entry.");
        System.out.println("Type '-edit' to edit an entry.");
        System.out.println("Type '-search' to search for words.");
        System.out.println("Type '-print' to export a txt file of your dictionary.");
    }

    public void dictionaryAdvanced() {
        boolean quit = false;

        //dictionaryManagement.insertFromFile();
        showAllWords();

        helpCommand();

        Scanner scanner = new Scanner(System.in);

        while(!quit) {
            String command = scanner.nextLine();

            switch (command) {
                case "-end":
                    quit = true;
                    break;

                case  "-show":
                    showAllWords();
                    break;

                case "-help":
                    helpCommand();
                    break;

                case "-lookup":
                    System.out.println("Enter word needed to be looked up: ");
                    String word = scanner.nextLine();
                    dictionaryManagement.dictionaryLookup(word);
                    break;

                case  "-add":
                    System.out.println("Enter word needed to be added: ");
                    String wordTarget = scanner.nextLine();
                    System.out.println("Enter meaning of that word: ");
                    String wordExplain= scanner.nextLine();
                    dictionaryManagement.addToDictionary(wordTarget, wordExplain);
                    System.out.println("Word added!");
                    break;

                case "-delete":
                    System.out.println("Enter word needed to be deleted: ");
                    String wordDelete = scanner.nextLine();
                    dictionaryManagement.deleteWord(wordDelete);
                    break;

                case "-edit":
                    System.out.println("Enter the entry needed to be edited: ");
                    int entry = scanner.nextInt();
                    if (entry > dictionaryManagement.dictionary.getCurrentSize()) {
                        System.out.println("Entry not existed");
                    } else {
                        String temp = scanner.nextLine();
                        System.out.println("Enter the word in English: ");
                        String wordEdit = scanner.nextLine();
                        System.out.println("Enter the word's meaning in Vietnamese: ");
                        String meaningEdit = scanner.nextLine();
                        dictionaryManagement.editWord(entry, wordEdit, meaningEdit);
                    }
                    break;

                case "-print":
                    dictionaryManagement.dictionaryExportToFile();
            }
        }
    }
}
