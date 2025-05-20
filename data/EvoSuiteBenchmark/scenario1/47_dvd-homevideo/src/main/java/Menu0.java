import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FilenameFilter;
import java.util.StringTokenizer;

/**
 * @author Shane Santner
 * This class creates a background menu for a DVD.  The user
 * can specify a background picture and audio to use for the
 * menu, or a default background can also be used.  The user
 * also has the option of specifying unique titles for each
 * chapter of the DVD.  If nothing is specified then the title
 * of the DVD will be used followed by an underscore and increasing
 * integer for each video clip.
 *
 * TODO - Can I use streams on dvd-menu?  Really need to standardize
 *        how I use streams across the board for all classes.
 */
public class Menu implements Runnable {

    public boolean init() {
        /* Create and start the new thread */
        m_Thread = new Thread(this);
        m_Thread.start();
        try {
            m_Thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        /* 
         * If an error occurred while executing DV_Capture then m_Error will be set
         * to true.  Also check the log file for any evidence of an error.
         */
        return (m_Error || m_GUI.ErrorCheck(m_GUI.strOutputDir + "/log/dvd-menu.log"));
    }
}
