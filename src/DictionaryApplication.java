import javax.swing.*;

public class DictionaryApplication extends JFrame {
    private Render render;

    public void runApplication() {
        render = new Render();
    }

    public static void main(String[] args) {
        DictionaryApplication app = new DictionaryApplication();
        app.runApplication();
    }
}
