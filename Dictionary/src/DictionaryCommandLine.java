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

    public static void main(String args[]) {
        DictionaryCommandLine command = new DictionaryCommandLine();
        command.dictionaryBasic();
    }
}
