package sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import common.ResourceService;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * represents a Sound file which can be played (in a Thread)
 * 
 * @author Ghazwan, Tobi
 * 
 */
public class Sound implements Runnable {

    String sndf;
    Player player = null;
    private static final Logger logger = Logger.getLogger(Sound.class);

    public Sound() {
    }

    public Sound(String soundFile) {
        sndf = soundFile;
    }

    /**
     * plays a MP3 File
     * 
     * @param filename
     */
    public void play(String filename) {
        InputStream fis = null;
        try {
            fis = ResourceService.getInputStream(filename);
        } catch (IllegalArgumentException e) {
            logger.info("mp3 not found: " + filename);
            return;
        }

        BufferedInputStream bis = null;
       
        try {
            bis = new BufferedInputStream(fis);

            player = new Player(bis);
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void run() {
   
        this.play(sndf);
         
    }
}
