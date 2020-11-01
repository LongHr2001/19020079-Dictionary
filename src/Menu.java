import javax.swing.*;

public class Menu extends JMenuBar {
    private DictionaryMenu dictionaryMenu;

    public Menu() {
        dictionaryMenu = new DictionaryMenu();
        add(dictionaryMenu);
    }

    public DictionaryMenu getDictionaryMenu() {
        return dictionaryMenu;
    }
}
