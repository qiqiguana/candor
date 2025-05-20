import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 * @author Shane Santner
 * The Capture class utilizes dvgrab to capture audio and video
 * data from a dv camcorder.  It captures for the amount of time
 * specified by the user.
 *
 * TODO - Is there a way to get the camcorder time from dvgrab?
 *        This would be a more accurate way of determining when
 *        to stop the camcorder.  Also need to handle input, output
 *        and error streams more appropriatly.
 */
public class Capture implements Runnable {

    /**
     * Creates a default instance of Capture
     */
    public Capture() {
    }

    /**
     * Creates a new instance of Capture
     * @param   min         The amount of time in minutes to capture audio/video
     * @param   sec         The amount of time in seconds to capture audio/video
     * @param   DVD_GUI     This is the GUI object used to control the form
     */
    public Capture(int min, int sec, GUI DVD_GUI) {
    }

    /**
     * Capture Member Variables
     */
    private int m_Minutes;

    private int m_Seconds;

    private GUI m_GUI;

    private Thread m_Thread;

    private boolean m_Error;

    private String m_BaseErr = "Capture Error - ";

    private String m_dvgrab = "dvgrab --autosplit --size 0 --format raw --opendml" + " --buffers 200 dv/dv_file-";

    /**
     * Used to instantiate a new thread and to perform error checking.
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean init();

    /**
     * Implements the run() method of the Runnable interface.  Makes multi-threading
     * possible.
     */
    public void run();

    /**
     * Captures audio and video from a dv camcorder by calling dvgrab
     */
    public void DV_Capture();
}
