import javax.swing.*;
import java.awt.*;

public class MeaningWord extends JTextArea {
    private final int MEANINGWORD_WIDTH = 595;
    private final int MEANINGWORD_HEIGHT = 45;

    public MeaningWord() {
        setFont(new Font("helveticaneuebold", Font.PLAIN, 25));
        setForeground(Color.RED);
        setEditable(false);
    }

    public int getHeight() {
        return MEANINGWORD_HEIGHT;
    }

    public int getWidth() {
        return MEANINGWORD_WIDTH;
    }
}
