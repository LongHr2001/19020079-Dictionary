import javax.swing.*;
import java.awt.*;

public class DeletePrompt extends JMenuItem {
    private final String DELETE_LABEL = "Delete this Word...";
    private final String DELETE_PROMPT = "Delete this Word? ";
    private final String DELETE_PROMPT_TITLE = "Delete Confirmation";
    private final String DELETE_SUCCESS = "Word deleted!";
    private final String DELETE_SUCCESS_TITLE = "Word Deleting Success";

    public DeletePrompt() {
        setText(DELETE_LABEL);
        setFont(new Font("helveticaneue", Font.PLAIN, 12));
    }

    public boolean deleteWordConfirmation() {
        JFrame frame = new JFrame();
        JOptionPane deleteWord = new JOptionPane();
        JLabel deleteWordPrompt = new JLabel(DELETE_PROMPT);
        deleteWordPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));

        int value = deleteWord.showConfirmDialog(frame, deleteWordPrompt, DELETE_PROMPT_TITLE, JOptionPane.YES_NO_OPTION);

        return value == JOptionPane.YES_OPTION;
    }

    public void deleteSuccess() {
        JFrame frame = new JFrame();
        JOptionPane deleteSuccess = new JOptionPane();
        JLabel deleteSuccessPrompt = new JLabel(DELETE_SUCCESS);
        deleteSuccessPrompt.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        deleteSuccess.showMessageDialog(frame, deleteSuccessPrompt, DELETE_SUCCESS_TITLE, JOptionPane.PLAIN_MESSAGE);

    }
}
