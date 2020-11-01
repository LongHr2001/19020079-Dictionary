import javax.swing.*;
import java.awt.*;

public class EditPrompt extends JMenuItem {
    private final String EDITWORD_LABEL = "Edit this Word";
    private final String EDITWORD_PROMPT = "Enter the new word: ";
    private final String EDITWORD_ERROR = "Word is not valid. Please try again.";
    private final String EDITWORD_ERROR_TITLE = "Word Edit Error";
    private final String EDITMEANING_PROMPT = "Enter the new word's new meaning: ";
    private final String EDITMEANING_ERROR = "Word is not valid. Please try again.";
    private final String EDITMEANING_ERROR_TITLE = "Word Edit Error";

    private String newWord;
    private String newMeaning;

    public EditPrompt() {
        setText(EDITWORD_LABEL);
        setFont(new Font("helveticaneue", Font.PLAIN, 12));
    }

    public boolean editWord() {
        JOptionPane editWord = new JOptionPane();
        JLabel editWordPrompt = new JLabel(EDITWORD_PROMPT);
        editWordPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

        String wordEdited = editWord.showInputDialog(editWordPrompt);

        if(wordEdited != null) {
            while (wordEdited.length() == 0) {
                JFrame frame = new JFrame();
                JOptionPane editWordError = new JOptionPane();
                JLabel editWordErrorPrompt = new JLabel(EDITWORD_ERROR);
                editWordErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                editWordError.showMessageDialog(frame, editWordErrorPrompt, EDITWORD_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                wordEdited = editWord.showInputDialog(editWordPrompt);
                if (wordEdited == null) {
                    break;
                }
            }

            if (wordEdited != null) {
                JOptionPane editMeaning = new JOptionPane();
                JLabel editMeaningPrompt = new JLabel(EDITMEANING_PROMPT);
                editMeaningPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

                String meaningEdited = editMeaning.showInputDialog(editMeaningPrompt);

                if (meaningEdited != null) {
                    while (meaningEdited.length() == 0) {
                        JFrame frame = new JFrame();
                        JOptionPane editMeaningError = new JOptionPane();
                        JLabel editMeaningErrorPrompt = new JLabel(EDITMEANING_ERROR);
                        editMeaningErrorPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
                        editMeaningError.showMessageDialog(frame, editMeaningErrorPrompt, EDITMEANING_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);

                        meaningEdited = editMeaning.showInputDialog(editMeaningPrompt);
                        if (meaningEdited == null) {
                            break;
                        }
                    }
                }
                if (meaningEdited != null) {
                    newWord = wordEdited;
                    newMeaning = meaningEdited;

                    return true;
                }
            }
        }

        return false;
    }

    public String getNewWord() {
        return newWord;
    }

    public String getNewMeaning() {
        return newMeaning;
    }
}
