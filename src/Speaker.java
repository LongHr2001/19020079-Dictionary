import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.JSMLException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
public class Speaker {

    public static void speak(String word) throws JSMLException, InterruptedException, EngineException, AudioException {
        System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

        Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
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
