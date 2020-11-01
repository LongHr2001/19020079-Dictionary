import javax.swing.*;
import java.awt.*;

public class SearchLabel extends JLabel {
    private final String LABEL_SEARCH = "Search: ";
    private final int SEARCH_WIDTH = 70;
    private final int SEARCH_HEIGHT = 20;

    public SearchLabel() {
        setText(LABEL_SEARCH);
        setFont(new Font("helveticaneue", Font.PLAIN, 15));
    }

    public int getHeight() {
        return SEARCH_HEIGHT;
    }

    public int getWidth() {
        return SEARCH_WIDTH;
    }
}
