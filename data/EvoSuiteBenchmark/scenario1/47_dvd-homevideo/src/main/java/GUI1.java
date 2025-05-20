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

    public int MessageBox(String message) {
        return JOptionPane.showConfirmDialog(null, message, "dvd-homevideo", JOptionPane.YES_NO_OPTION);
    }
}
