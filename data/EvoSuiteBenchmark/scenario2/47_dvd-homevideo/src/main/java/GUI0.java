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

    /**
     * On startup, checks to ensure that the raw1394 module is loaded.
     * This module is only needed to capture audio and video from the
     * dv camcorder.
     * return   A boolean indicating if the module is present
     */
    public boolean checkForModules() {
        try {
            String line;
            String modules = "cat /proc/modules | grep raw1394";
            String[] module_cmd = { "/bin/sh", "-c", modules };
            Process p = Runtime.getRuntime().exec(module_cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            line = in.readLine();
            p.waitFor();
            if (line == null)
                return false;
            else
                return true;
        } catch (Exception ioe) {
            /* If we have made it here then the module is not loaded */
            return false;
        }
    }
}
