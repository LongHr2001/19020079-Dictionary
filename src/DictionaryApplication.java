import javax.speech.EngineException;
import javax.swing.*;

public class DictionaryApplication extends JFrame {
    private Render render;

    public void runApplication() throws EngineException {
        render = new Render();
    }

    public static void main(String[] args) throws EngineException {
        DictionaryApplication app = new DictionaryApplication();
        app.runApplication();
    }
}
