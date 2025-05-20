import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringIndexOutOfBoundsException;
import java.util.StringTokenizer;

/**
 * @author Shane Santner
 * This class is used to transcode the video captured
 * from the digital camcorder into mpeg4, the DVD compatible
 * format.  It explicitly calls the transcode command
 * after meticulously preparing the options that can be passed
 * to transcode.
 *
 * TODO - Break mplex out into its own class.
 *        Figure out how to calculate remaining time even if
 *        video was not captured from the camcorder during the
 *        current session.
 *        Need to handle input, output and error streams
 *        more appropriatly.
 */
public class Convert implements Runnable {

    /**
     * Used to instantiate a new thread and to perform error checking.
     *
     * @return A boolean to determine if an error occurred in the function
     */
    public boolean init();
}
