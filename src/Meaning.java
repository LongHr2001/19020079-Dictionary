import javax.swing.*;
import java.awt.*;

public class Meaning{
    private final int MEANING_WIDTH = 655;
    private final int MEANING_HEIGHT = 490;

    private JTextArea meaning;

    private JScrollPane meaningScroll;

    public Meaning() {
        meaning = new JTextArea();

        meaning.setFont(new Font("helveticaneue", Font.PLAIN, 15));
        meaning.setEditable(false);

        meaningScroll = new JScrollPane(meaning);
        meaningScroll.setBorder(BorderFactory.createEmptyBorder());
    }

    public int getHeight() {
        return MEANING_HEIGHT;
    }

    public int getWidth() {
        return MEANING_WIDTH;
    }

    public JScrollPane getMeaningScroll() {
        return meaningScroll;
    }

    public JTextArea getMeaning() {
        return meaning;
    }
}
