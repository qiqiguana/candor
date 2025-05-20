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
     * Used to instantiate a new thread and to perform error checking.
     *
     * @return A boolean to determine if an error occurred in the function
     */
    public boolean init();
}
