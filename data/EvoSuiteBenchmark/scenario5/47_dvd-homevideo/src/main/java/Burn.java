import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Shane Santner
 * This class burns the DVD file structure previously created by
 * dvd-author to a DVD disc.
 */
public class Burn {

    /**
     * Optionally burn to DVD when complete and no errors, uses growisofs
     *
     * @param DVD_GUI This is the GUI object used to control the form
     * return   A boolean to determine if an error occurred in the function
     */
    public boolean BurnToDVD(GUI DVD_GUI) {
        /* Update growisofs command with the correct paths */
        m_growisofs = m_growisofs.replaceAll("temp", DVD_GUI.strOutputDir);
        /* 
         * Burn object always gets created, so m_Burn will not always be true,
         * must be checked. 
         */
        if (m_Burn == true) {
            try {
                /* Create the growisofs process which actually burns the DVD */
                Process p = Runtime.getRuntime().exec(m_growisofs);
                /* Instantiate input and output readers for dvd-author streams */
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader err_in = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                BufferedWriter out = new BufferedWriter(new FileWriter(DVD_GUI.strOutputDir + "/log/burn.log"));
                BufferedWriter err_out = new BufferedWriter(new FileWriter(DVD_GUI.strOutputDir + "/log/burn.err"));
                String line;
                /* We will miss the error without this delay */
                Thread.sleep(50);
                /* 
                 * Check for the case where the necessary dependencies are not
                 * installed, but the user tries to run dvd-homevideo anyway
                 */
                if (err_in.ready()) {
                    line = err_in.readLine();
                    if (line.equals("/bin/sh: growisofs: command not found")) {
                        DVD_GUI.MessageBox("Could not locate growisofs in your path." + "\nPlease install all necessary dependencies" + "\nand rerun dvd-homevideo.", 0);
                        /* Close all open streams */
                        in.close();
                        out.close();
                        err_in.close();
                        err_out.close();
                        /* Indicate that there was an error */
                        return true;
                    } else {
                        DVD_GUI.txtAreaOutput.append(line + "\n");
                        out.write(line);
                        out.newLine();
                    }
                }
                out.write("burn.log--->dvd-homevideo");
                out.newLine();
                /* Write the output to the txtAreaOutput user display */
                while ((line = in.readLine()) != null) {
                    DVD_GUI.txtAreaOutput.append(line + "\n");
                    out.write(line);
                    out.newLine();
                }
                /*
                 * close() throws an IOException
                 */
                in.close();
                out.close();
                err_in.close();
                err_out.close();
                /*
                 * p.waitFor() throws an InterruptedException
                 */
                p.waitFor();
            } catch (IOException ex) {
                SaveStackTrace.printTrace(DVD_GUI.strOutputDir, ex);
                DVD_GUI.MessageBox(m_BaseErr + "IO Error\n" + ex.toString(), 0);
                ex.printStackTrace();
                return true;
            } catch (InterruptedException ex) {
                SaveStackTrace.printTrace(DVD_GUI.strOutputDir, ex);
                DVD_GUI.MessageBox(m_BaseErr + "dvd-homevideo thread was interrupted\n" + ex.toString(), 0);
                ex.printStackTrace();
                return true;
            } catch (NullPointerException ex) {
                SaveStackTrace.printTrace(DVD_GUI.strOutputDir, ex);
                DVD_GUI.MessageBox(m_BaseErr + "Error executing Runtime.getRuntime().exec()\n" + ex.toString(), 0);
                ex.printStackTrace();
                return true;
            } catch (IllegalArgumentException ex) {
                SaveStackTrace.printTrace(DVD_GUI.strOutputDir, ex);
                DVD_GUI.MessageBox(m_BaseErr + "Illegal argument sent to Runtime.getRuntime().exec()\n" + ex.toString(), 0);
                ex.printStackTrace();
                return true;
            } catch (Exception ex) {
                SaveStackTrace.printTrace(DVD_GUI.strOutputDir, ex);
                DVD_GUI.MessageBox(m_BaseErr + "Unknown Error occurred\n" + ex.toString(), 0);
                ex.printStackTrace();
                return true;
            }
        }
        if (m_Burn)
            return DVD_GUI.ErrorCheck(DVD_GUI.strOutputDir + "/log/burn.log");
        return false;
    }
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
