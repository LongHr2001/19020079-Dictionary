import java.util.Objects;
import java.util.Scanner;

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

    public void dictionaryAdvanced() {
        dictionaryManagement.insertFromFile();
        showAllWords();

        System.out.println("You can search for a word in this dictionary.");
        System.out.println("Type '-1'  to end the program.");

        Scanner scanner = new Scanner(System.in);

        while(true) {
            String lookup = scanner.nextLine();

            if(Objects.equals(lookup, "-1")) {
                break;
            } else {
                dictionaryManagement.dictionaryLookup(lookup);
            }
        }
    }

    public static void main(String[] args) {
        DictionaryCommandLine command = new DictionaryCommandLine();
        command.dictionaryAdvanced();
    }
}
