import javax.swing.*;
import java.awt.*;

public class AddPrompt extends JMenuItem {
    private final String ADD_LABEL = "Add a Word...";
    private final String ADDWORD_PROMPT = "Enter word needed to be added: ";
    private final String ADDWORD_ERROR = "Word is not valid. Please try again.";
    private final String ADDWORD_ERROR_TITLE = "Word Adding Error";
    private final String ADDMEANING_PROMPT = "Enter meaning of the word: ";
    private final String ADDMEANING_ERROR = "Meaning is not valid. Please try again.";
    private final String ADDMEANING_ERROR_TITLE = "Meaning Adding Error";

    private Word wordAdded = new Word("", "");

    public AddPrompt() {
        setText(ADD_LABEL);
        setFont(new Font("helveticaneue", Font.PLAIN, 12));
    }

    public boolean addWord(){
        JOptionPane addWord = new JOptionPane();
        JLabel addWordPrompt = new JLabel(ADDWORD_PROMPT);
        addWordPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

        String wordToAdd = addWord.showInputDialog(addWordPrompt);

        if(wordToAdd != null) {
            while (wordToAdd.length() == 0) {
                JFrame frame = new JFrame();
                JOptionPane addWordError = new JOptionPane();
                JLabel addWordErrorPrompt = new JLabel(ADDWORD_ERROR);
                addWordErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                addWordError.showMessageDialog(frame, addWordErrorPrompt, ADDWORD_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                wordToAdd = addWord.showInputDialog(addWordPrompt);
                if (wordToAdd == null) {
                    break;
                }
            }

            if(wordToAdd != null) {
                JOptionPane addMeaning = new JOptionPane();
                JLabel addMeaningPrompt = new JLabel(ADDMEANING_PROMPT);
                addMeaningPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

                String meaningToAdd = addMeaning.showInputDialog(addMeaningPrompt);

                if (meaningToAdd != null) {
                    while (meaningToAdd.length() == 0) {
                        JFrame frame = new JFrame();
                        JOptionPane addMeaningError = new JOptionPane();
                        JLabel addMeaningErrorPrompt = new JLabel(ADDMEANING_ERROR);
                        addMeaningErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                        addMeaningError.showMessageDialog(frame, addMeaningErrorPrompt, ADDMEANING_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                        meaningToAdd = addMeaning.showInputDialog(addMeaningPrompt);
                        if (meaningToAdd == null) {
                            break;
                        }
                    }
                }
                if (meaningToAdd != null) {
                    wordAdded = new Word(wordToAdd, meaningToAdd);
                    return true;
                }
            }
        }

        return false;
    }

    public Word getWordAdded() {
        return wordAdded;
    }
}
