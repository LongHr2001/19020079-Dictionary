import javax.swing.*;
import java.awt.*;

public class DictionaryMenu extends JMenu {
    private AddPrompt add;
    private DeletePrompt delete;
    private EditPrompt edit;

    private final String DICTIONARY_LABEL = "Dictionary";

    public DictionaryMenu() {
        setText(DICTIONARY_LABEL);
        setFont(new Font("helveticaneue", Font.PLAIN, 12));
        add = new AddPrompt();
        delete = new DeletePrompt();
        edit = new EditPrompt();
        add(add);
        add(delete);
        add(edit);
    }

    public AddPrompt getAdd() {
        return add;
    }

    public DeletePrompt getDelete() {
        return delete;
    }

    public EditPrompt getEdit() {
        return edit;
    }
}
