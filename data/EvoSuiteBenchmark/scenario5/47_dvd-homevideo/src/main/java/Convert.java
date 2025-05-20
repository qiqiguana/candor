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
    public boolean init() {
        if (m_GUI.menuChkThread.isSelected()) {
            m_Thread = new Thread(this);
            m_Thread.start();
            try {
                m_Thread.join();
            } catch (InterruptedException ex) {
                SaveStackTrace.printTrace(m_GUI.strOutputDir, ex);
                m_GUI.MessageBox(m_BaseErr + "Could not join Convert.java thread\n" + ex.toString(), 0);
                ex.printStackTrace();
                m_Error = true;
            }
        } else
            Transcode();
        /* 
         * Check transcode.log and mplex.log for any sign of an error.
         * If either file contains an error then return an error.  Also
         * check m_Error to see if it was set while executing the 
         * Transcode method.
         */
        return (m_Error || m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/transcode.log") || m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/mplex.log"));
    }

    /**
     * Encodes dv files to mpeg using transcode, then uses mplex to combine
     * the .ac3 audio and .m2v video files into a DVD compatible .vob file
     */
    public void Transcode();
}

/**
 * SaveStackTrace is a debugging class that provides one utility method.
 * The method takes an object of type Throwable as an argument and sends
 * the contents of printStackTrace to a file for better viewing.
 * Usage: Call from a catch block like this:
 *        try
 *        {
 *          ...
 *   }
 *   catch(Exception ex)
 *        {
 *       SaveStackTrace.printTrace(ex);
 *        }
 */
public class SaveStackTrace {

    /**
     * The static method that logs all exceptions to a file.
     *
     * @param path location to write the error file
     * @param e the Throwable object
     */
    public static void printTrace(String path, Throwable e);
}

/**
 * @author Shane Santner
 * This class provides the GUI for dvd-homevideo.
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Displays a message box with the supplied text and type
     *
     * @param message The message to display in the box
     * @param type The type of message (Info, Warning or Error)
     */
    public void MessageBox(String message, int type);

    /**
     * Check dvd-homevideo for runtime errors
     * @param   fileName    This is a log file to parse through, looking for
     *                      the keyword "Error"
     * @return   A boolean indicating if an error was found
     */
    public boolean ErrorCheck(String fileName);
}
