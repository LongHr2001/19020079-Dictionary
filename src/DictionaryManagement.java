import javax.swing.*;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();

    public DictionaryManagement() {
        insertFromFile();
    }

    void insertFromCommandLine() {
        Scanner scannerInt = new Scanner(System.in);

        System.out.println("Nhap so tu can them:");

        int numOfWords = scannerInt.nextInt();
        dictionary.setCurrentSize(numOfWords);

        System.out.println("Nhap tu va nghia cua chung:");

        Scanner scannerString = new Scanner(System.in);

        for(int i = 0; i < numOfWords ; i++) {
            String wordTarget = scannerString.nextLine();
            String wordExplain = scannerString.nextLine();

            Word wordTemp =  new Word(wordTarget, wordExplain);
            dictionary.addWord(wordTemp);
        }
    }

    public void insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("dictionary.txt"), StandardCharsets.UTF_8));

            boolean firstWord = true;
            String word = "";
            String meaning = "";

            while(reader.ready()) {
                String line = reader.readLine();
                int length = line.length();
                //System.out.println(line);

                if (!Objects.equals(line, "")) {
                    if (line.charAt(0) == '@') {
                        if (!firstWord) {
                            Word temp = new Word(word, meaning);
                            dictionary.addWord(temp);
                            meaning = "";

                        } else {
                            firstWord = false;
                        }

                        boolean separated = false;

                        for (int i = 0; i < length; i++) {
                            if (line.charAt(i) == '/' || line.charAt(i) == '=') {
                                word = line.substring(1, i);
                                separated = true;
                                break;
                            }
                        }

                        if (!separated) {
                            word = line.substring(1, length);
                        }

                    } else {
                        meaning += " " + line.substring(1, length);
                        meaning += System.lineSeparator();
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int dictionaryLookup(String word) {
        int dictLength = dictionary.getCurrentSize();

        for (int i = 0; i < dictLength; i++) {
            if(Objects.equals(word, dictionary.getDictAtElement(i).getWordTarget())) {
                return i;
            }
        }

        return -1;
    }

    public boolean existedInDictionary(String word) {
        int dictLength = dictionary.getCurrentSize();

        for (int i = 0; i < dictLength; i++) {
            if(Objects.equals(word, dictionary.getDictAtElement(i).getWordTarget())) {
                return true;
            }
        }

        return false;
    }

    public void addToDictionary(String wordTarget, String wordExplain) {
        if(existedInDictionary(wordTarget)) {
            System.out.println("Word already existed in Dictionary");
        } else {
            Word word = new Word(wordTarget, wordExplain);
            dictionary.addWord(word);
        }
    }

    public void addToDictionary(Word word) {
        if(existedInDictionary(word.getWordTarget())) {
            System.out.println("Word already existed in Dictionary");
        } else {
            dictionary.addWord(word);
        }
    }

    public void deleteWord(String word) {
        dictionary.deleteWord(word);
    }

    public void editWord(int entry, String wordTarget, String wordExplain) {
        Word word = new Word(wordTarget, wordExplain);
        dictionary.setDictAtElement(entry - 1, word);
        System.out.println("Entry edited!");
    }

    public void dictionaryExportToFile() {
        File export = new File("DictionaryExported.txt");

        int dictSize = dictionary.getCurrentSize();

        try {
            if (export.createNewFile()) {
                FileWriter writer = new FileWriter("DictionaryExported.txt");
                writer.write("No      |English        |Vietnamese");

                for(int i = 0; i < dictSize; i++) {
                    writer.write((i+1) + "        |" + dictionary.getDictAtElement(i).getWordTarget()
                            + "        |" + dictionary.getDictAtElement(i).getWordExplain() + "\n");
                }

                writer.close();

                System.out.println("File exported!");
            } else {
                FileWriter writer = new FileWriter("DictionaryExported.txt");
                writer.write("No      |English        |Vietnamese" + System.lineSeparator());

                for(int i = 0; i < dictSize; i++) {
                    writer.write((i+1) + "        |" + dictionary.getDictAtElement(i).getWordTarget()
                            + "        |" + dictionary.getDictAtElement(i).getWordExplain() + System.lineSeparator());
                }

                writer.close();

                System.out.println("File overwritten!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DefaultListModel<Word> dictionarySearcher(String search) {
        search = search.toLowerCase();
        int dictSize = dictionary.getCurrentSize();
        int searchStringLength = search.length();

        DefaultListModel<Word> resultList = new DefaultListModel();

        for (int i = 0; i < dictSize; i++) {
            String word = dictionary.getDictAtElement(i).getWordTarget().toLowerCase();
            if(word.length() >= searchStringLength) {
                String wordSubString = word.substring(0, searchStringLength);

                if (wordSubString.equals(search)) {
                    resultList.addElement(dictionary.getDictAtElement(i));
                }
            }
        }

        return resultList;
    }
}
