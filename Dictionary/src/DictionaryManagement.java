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
            dictionary.setDictAtElement(i, wordTemp);
        }
    }

    public void insertFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("dictionary.txt"), StandardCharsets.UTF_16));

            int wordCounter = 0;
            while(reader.ready()) {
                String wordAndMeaning = reader.readLine();

                int length = wordAndMeaning.length();

                for(int i = 0; i < length; i++) {
                    if (wordAndMeaning.charAt(i) == '\t') {
                        String wordTarget = wordAndMeaning.substring(0, i);
                        String wordExplain = wordAndMeaning.substring(i + 1, length);

                        Word wordTemp =  new Word(wordTarget, wordExplain);
                        dictionary.setDictAtElement(wordCounter, wordTemp);
                        break;
                    }
                }

                wordCounter++;
            }

            reader.close();

            dictionary.setCurrentSize(wordCounter);
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
}
