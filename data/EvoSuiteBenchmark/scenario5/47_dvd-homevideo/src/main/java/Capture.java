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
 * This class provides the GUI for dvd-homevideo.
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Check dvd-homevideo for runtime errors
     *
     * @param fileName This is a log file to parse through, looking for
     *                      the keyword "Error"
     * @return A boolean indicating if an error was found
     */
    public boolean ErrorCheck(String fileName);
}

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
     * Used to instantiate a new thread and to perform error checking.
     *
     * @return A boolean to determine if an error occurred in the function
     */
    public boolean init() {
        if (m_GUI.menuChkThread.isSelected()) {
            /* Create and start the new thread */
            m_Thread = new Thread(this);
            /* Increase priority so we don't drop frames */
            m_Thread.setPriority(10);
            m_Thread.start();
            /* Need to fix this */
            return false;
        } else {
            DV_Capture();
            return (m_Error | m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/dvgrab.log"));
        }
        /* 
         * If an error occurred while executing DV_Capture then m_Error will be set
         * to true.  Also check the log file for any evidence of an error.
         */
    }

    /**
     * Captures audio and video from a dv camcorder by calling dvgrab
     */
    public void DV_Capture();
}
