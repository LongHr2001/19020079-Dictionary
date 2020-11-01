import java.awt.*;
import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.JSMLException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.swing.*;

public class Speaker extends JButton {
    Synthesizer synthesizer;

    public void prepareSpeaker() throws EngineException {
        System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

        synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
    }

    public Speaker() throws EngineException {
        Icon speakIcon = new ImageIcon("speakerIcon.png");
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setIcon(speakIcon);
        setPreferredSize(new Dimension(30, 30));

        prepareSpeaker();
    }

    public void speak(String word) throws JSMLException, InterruptedException, EngineException, AudioException {
        if(synthesizer != null) {
            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.speak(word, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
        } else {
            System.out.println("null");
        }
    }
}
