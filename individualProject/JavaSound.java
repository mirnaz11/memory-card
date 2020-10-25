package individualProject;

import java.net.URL;
import java.applet.*;

public class JavaSound {
	
    public void playFile(String file) {
    	
        try {
            URL soundToPlay = getClass().getResource(file);
            AudioClip player = Applet.newAudioClip(soundToPlay);
            player.play();
        } catch (Exception e) {}
    }
}