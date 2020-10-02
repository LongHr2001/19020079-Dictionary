import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dictionary = new Dictionary();

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
                    new FileInputStream("dictionary.txt"), StandardCharsets.UTF_16));

            while(reader.ready()) {
                String wordAndMeaning = reader.readLine();

                int length = wordAndMeaning.length();

                for(int i = 0; i < length; i++) {
                    if (wordAndMeaning.charAt(i) == '\t') {
                        String wordTarget = wordAndMeaning.substring(0, i);
                        String wordExplain = wordAndMeaning.substring(i + 1, length);

                        Word wordTemp =  new Word(wordTarget, wordExplain);
                        dictionary.addWord(wordTemp);
                        break;
                    }
                }

            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup(String word) {
        int dictLength = dictionary.getCurrentSize();

        boolean found = false;

        for (int i = 0; i < dictLength; i++) {
            if(Objects.equals(word, dictionary.getDictAtElement(i).getWordTarget())) {
                System.out.println(dictionary.getDictAtElement(i).getWordExplain());
                found = true;
            }
        }
        if(!found) {
            System.out.println("This word doesn't exist in this dictionary.");
        }
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

    public void deleteWord(String word) {
        boolean deleted = dictionary.deleteWord(word);

        if(deleted) {
            System.out.println("Word deleted!");
        } else {
            System.out.println("Word not existed in Dictionary");
        }
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
}
