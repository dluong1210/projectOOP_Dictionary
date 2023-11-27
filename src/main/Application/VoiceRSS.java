package application;

import java.io.FileOutputStream;
import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.AudioFormat;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

public class VoiceRSS {
    final private static String myKey = "264f510b3e644c54a955b15b8c5c6e5f";

    public static void setAudio(String text, String langue, String nameFile) throws Exception {
        VoiceProvider tts = new VoiceProvider(myKey);
        VoiceParameters params = new VoiceParameters(text, langue);

        params.setCodec(AudioCodec.MP3);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("src/resources/audio/" + nameFile + ".wav");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
    }
    public static void main (String args[]) throws Exception {
        String test = "Lucas goes to school every day of the week. He has many subjects to go to each school day: "
                + "English, art, science, mathematics, gym, and history. His mother packs a big backpack full of"
                + " books and lunch for Lucas.";
        setAudio(test, Languages.English_UnitedStates, "test");
    }
}