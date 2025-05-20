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
 * @author Shane Santner
 * This class provides the GUI for dvd-homevideo.
 */
public class GUI extends javax.swing.JFrame {

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
}
