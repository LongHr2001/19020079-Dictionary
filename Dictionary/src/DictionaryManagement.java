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
}
