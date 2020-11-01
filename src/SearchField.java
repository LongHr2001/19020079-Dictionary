import javax.swing.*;
import java.awt.*;

public class SearchField extends JTextArea {
    private final int SEARCHFIELD_WIDTH = 315;
    private final int SEARCHFIELD_HEIGHT = 20;

    public SearchField() {
        setFont(new Font("helveticaneue", Font.PLAIN, 15));
    }

    public int getHeight() {
        return SEARCHFIELD_HEIGHT;
    }

    public int getWidth() {
        return SEARCHFIELD_WIDTH;
    }
}
