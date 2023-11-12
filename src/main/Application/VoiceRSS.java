package Application;

import java.io.FileOutputStream;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.SpeechDataEvent;
import com.voicerss.tts.SpeechDataEventListener;
import com.voicerss.tts.SpeechErrorEvent;
import com.voicerss.tts.SpeechErrorEventListener;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

public class VoiceRSS {
    final private static String myKey = "264f510b3e644c54a955b15b8c5c6e5f";

    public static void setAudio(String text, String langue, String nameFile) {
        VoiceProvider tts = new VoiceProvider(myKey);

        VoiceParameters params = new VoiceParameters(text, langue);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        tts.addSpeechErrorEventListener(new SpeechErrorEventListener() {
            @Override
            public void handleSpeechErrorEvent(SpeechErrorEvent e) {
                System.out.print(e.getException().getMessage());
            }
        });

        tts.addSpeechDataEventListener(new SpeechDataEventListener() {
            @Override
            public void handleSpeechDataEvent(SpeechDataEvent e) {
                try {
                    byte[] voice = (byte[]) e.getData();

                    FileOutputStream fos = new FileOutputStream("src/resources/audio/" + nameFile + ".wav");
                    fos.write(voice, 0, voice.length);
                    fos.flush();
                    fos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        tts.speechAsync(params);
    }

    public static void main(String args[]) throws Exception {
        String test = "Lucas goes to school every day of the week. He has many subjects to go to each school day: "
                + "English, art, science, mathematics, gym, and history. His mother packs a big backpack full of"
                + " books and lunch for Lucas.";
        setAudio(test, Languages.English_UnitedStates, "test");
    }
}