//import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileFilter;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.StringTokenizer;

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
     * Check dvd-homevideo for runtime errors
     *
     * @param fileName This is a log file to parse through, looking for
     *                      the keyword "Error"
     * @return A boolean indicating if an error was found
     */
    public boolean ErrorCheck(String fileName) {
        String line;
        StringTokenizer st;
        String testToken;
        boolean error = false;
        /*
         * Need to parse through the supplies file and look for any
         * instances of the words 'error' or 'broken'.  If found, this
         * is treated as a run-time error of one of the dependent programs
         * that dvd-homevideo relies on.
         */
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            while ((line = in.readLine()) != null) {
                /* Check for a specific burn error */
                if (line.equals(":-( /dev/dvd: media is not recognized as recordable DVD: 9")) {
                    MessageBox("Non-recoverable error occurred." + "\nClass Name: " + new Exception().getStackTrace()[1].getClassName() + "\nMethod Name: " + new Exception().getStackTrace()[1].getMethodName() + "\nError was: " + line, 0);
                    return true;
                }
                /*
                 * Need to catch all instances of 'error' or 'broken'.  This
                 * includes cases such as:
                 * **Error:
                 * error~
                 * Broken pipe
                 * etc, etc, etc.  We need to catch ALL instances.
                 */
                st = new StringTokenizer(line, "*,;:'-~\t ");
                while (st.hasMoreTokens()) {
                    testToken = st.nextToken();
                    if (// ||
                    testToken.equalsIgnoreCase("Error")) //testToken.equalsIgnoreCase("Broken"))
                    {
                        MessageBox("Non-recoverable error occurred." + "\nClass Name: " + new Exception().getStackTrace()[1].getClassName() + "\nMethod Name: " + new Exception().getStackTrace()[1].getMethodName() + "\nError was: " + line, 0);
                        error = true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            SaveStackTrace.printTrace(strOutputDir, ex);
            MessageBox("Can not find " + fileName + "\n" + ex.toString(), 0);
            ex.printStackTrace();
            return true;
        } catch (IOException ex) {
            SaveStackTrace.printTrace(strOutputDir, ex);
            MessageBox("IO Error\n" + ex.toString(), 0);
            ex.printStackTrace();
            return true;
        }
        return error;
    }

    /**
     * Displays a message box with the supplied text and type
     * @param   message    The message to display in the box
     * @param   type       The type of message (Info, Warning or Error)
     */
    public void MessageBox(String message, int type);
}
