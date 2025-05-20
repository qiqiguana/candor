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

    /* 
     * Creates a new form, probably the most compilcated - or
     * messiest constructor I have ever developed.
     */
    public GUI() {
    }

    javax.swing.ImageIcon image = new javax.swing.ImageIcon(getClass().getResource("/logo_32x32.png"));

    protected String strOutputDir;

    protected boolean blnBegin;

    /* My list of dependent programs for dvd-homevideo */
    protected String[] DependentPrograms = { "dvgrab", "transcode", "mplex", "dvd-menu", "dvdauthor", "growisofs", "ffmpeg", "lame", "sox" };

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents();

    private //GEN-FIRST:event_menuRd_IgnoreNoneStateChanged
    void //GEN-FIRST:event_menuRd_IgnoreNoneStateChanged
    menuRd_IgnoreNoneStateChanged(//GEN-FIRST:event_menuRd_IgnoreNoneStateChanged
    javax.swing.event.ChangeEvent evt);

    private //GEN-FIRST:event_menuRd_IgnoreCapConvMenuStateChanged
    void //GEN-FIRST:event_menuRd_IgnoreCapConvMenuStateChanged
    menuRd_IgnoreCapConvMenuStateChanged(//GEN-FIRST:event_menuRd_IgnoreCapConvMenuStateChanged
    javax.swing.event.ChangeEvent evt);

    private //GEN-FIRST:event_menuRd_IgnoreCapConvStateChanged
    void //GEN-FIRST:event_menuRd_IgnoreCapConvStateChanged
    menuRd_IgnoreCapConvStateChanged(//GEN-FIRST:event_menuRd_IgnoreCapConvStateChanged
    javax.swing.event.ChangeEvent evt);

    private //GEN-FIRST:event_menuRd_IgnoreCapStateChanged
    void //GEN-FIRST:event_menuRd_IgnoreCapStateChanged
    menuRd_IgnoreCapStateChanged(//GEN-FIRST:event_menuRd_IgnoreCapStateChanged
    javax.swing.event.ChangeEvent evt);

    private //GEN-FIRST:event_txtOutputDirFocusLost
    void //GEN-FIRST:event_txtOutputDirFocusLost
    txtOutputDirFocusLost(//GEN-FIRST:event_txtOutputDirFocusLost
    java.awt.event.FocusEvent evt);

    private //GEN-FIRST:event_txtTitleFocusLost
    void //GEN-FIRST:event_txtTitleFocusLost
    txtTitleFocusLost(//GEN-FIRST:event_txtTitleFocusLost
    java.awt.event.FocusEvent evt);

    private //GEN-FIRST:event_btnOpen_OutputDirKeyTyped
    void //GEN-FIRST:event_btnOpen_OutputDirKeyTyped
    btnOpen_OutputDirKeyTyped(//GEN-FIRST:event_btnOpen_OutputDirKeyTyped
    java.awt.event.KeyEvent evt);

    private //GEN-FIRST:event_btnOpen_OutputDirMouseClicked
    void //GEN-FIRST:event_btnOpen_OutputDirMouseClicked
    btnOpen_OutputDirMouseClicked(//GEN-FIRST:event_btnOpen_OutputDirMouseClicked
    java.awt.event.MouseEvent evt);

    private //GEN-FIRST:event_spnSecondsStateChanged
    void //GEN-FIRST:event_spnSecondsStateChanged
    spnSecondsStateChanged(//GEN-FIRST:event_spnSecondsStateChanged
    javax.swing.event.ChangeEvent evt);

    private //GEN-FIRST:event_spnMinutesStateChanged
    void //GEN-FIRST:event_spnMinutesStateChanged
    spnMinutesStateChanged(//GEN-FIRST:event_spnMinutesStateChanged
    javax.swing.event.ChangeEvent evt);

    private void btnStartKeyTyped(java.awt.event.KeyEvent evt);

    private void btnExitKeyTyped(java.awt.event.KeyEvent evt);

    private void menuChkThreadItemStateChanged(java.awt.event.ItemEvent evt);

    private void menuAboutMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the Exit button.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void btnExitMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user changing the state of one
     * of the menu radio buttions.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void menuRd_16_9ItemStateChanged(java.awt.event.ItemEvent evt);

    /**
     * This is the code that handles the user changing the state of one
     * of the menu radio buttions.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void menuRd_4_3ItemStateChanged(java.awt.event.ItemEvent evt);

    /**
     * This is the code that handles the user changing the state of one
     * of the menu radio buttions.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void menuRdPALItemStateChanged(java.awt.event.ItemEvent evt);

    /**
     * This is the code that handles the user changing the state of one
     * of the menu radio buttions.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void menuRdNTSCItemStateChanged(java.awt.event.ItemEvent evt);

    /**
     * This is the code that handles the user clicking the Play button.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void btnPlayMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking on the SubmitBug button
     * in the Help menu.
     * @param   evt    The mousePressed event handled by this method
     */
    private void menuBugMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the Open button
     * from the File menu.
     * @param   evt    The mousePressed event handled by this method
     */
    private void menuOpenMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the Save button
     * from the File menu.
     * @param   evt    The mousePressed event handled by this method
     */
    private void menuSaveMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the dvd-homevideo website
     * button from the Help menu.
     * @param   evt    The mousePressed event handled by this method
     */
    private void menuInternetMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the README file
     * button from the Help menu.
     * @param   evt    The mousePressed event handled by this method
     */
    private void menuREADMEMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the Exit button
     * from the File menu.
     * @param   evt    The mousePressed event handled by this method
     */
    private void menuExitMousePressed(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking the Start button.
     * @param   evt    The mouseClicked event handled by this method
     */
    private void btnStartMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user typing Return on the
     * Open button to search for a title file to use for dvd-menu.
     * @param   evt    The KeyTyped event handled by this method
     */
    private void btnOpen_TextFileKeyTyped(java.awt.event.KeyEvent evt);

    /**
     * This is the code that handles the user typing Return on the
     * Open button to search for an output directory to locate an
     * audio file.
     * @param   evt    The KeyTyped event handled by this method
     */
    private void btnOpen_AudioKeyTyped(java.awt.event.KeyEvent evt);

    /**
     * This is the code that handles the user typing Return on the
     * Open button to search for an output directory to locate a
     * picture in.
     * @param   evt    The KeyTyped event handled by this method
     */
    private void btnOpen_PictureKeyTyped(java.awt.event.KeyEvent evt);

    /**
     * This is the code that handles the user typing the Space bar
     * on the check box for the menu
     * @param   evt    The KeyTyped event handled by this method
     */
    private void chkMenuKeyTyped(java.awt.event.KeyEvent evt);

    /**
     * This is the code that handles the user typing the Space bar
     * on the check box for the quality group of radio buttons
     * @param   evt    The KeyTyped event handled by this method
     */
    private void chkQualityKeyTyped(java.awt.event.KeyEvent evt);

    /**
     * This is the code that handles the user clicking on the
     * Open button to search for an output directory to locate a
     * picture in.
     * @param   evt    The KeyTyped event handled by this method
     */
    private void btnOpen_PictureMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking on the
     * Open button to search for an output directory to locate an
     * audio file in.
     * @param   evt    The KeyTyped event handled by this method
     */
    private void btnOpen_AudioMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking on the
     * Open button to search for a title file to be used for
     * the background menu of the DVD
     * @param   evt    The KeyTyped event handled by this method
     */
    private void btnOpen_TextFileMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking on the
     * check box to select the quality of video conversion for
     * their DVD
     * @param   evt    The KeyTyped event handled by this method
     */
    private void chkQualityMouseClicked(java.awt.event.MouseEvent evt);

    /**
     * This is the code that handles the user clicking on the
     * check box to specify details for the DVD menu
     * @param   evt    The KeyTyped event handled by this method
     */
    private void chkMenuMouseClicked(java.awt.event.MouseEvent evt);

    public void enableStartButton();

    /**
     * On startup, checks to ensure that the raw1394 module is loaded.
     * This module is only needed to capture audio and video from the
     * dv camcorder.
     * return   A boolean indicating if the module is present
     */
    public boolean checkForModules();

    /**
     * On startup, checks to ensure that all dependent programs are installed
     * @param   Prog[]  List of dependent programs needed for dvd-homevideo
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean checkForProg(String[] Prog);

    /**
     * Displays a message box with the supplied text and yes/no options
     * @param   message    The message to display in the box
     */
    public int MessageBox(String message);

    /**
     * Displays a message box with the supplied text and type
     * @param   message    The message to display in the box
     * @param   type       The type of message (Info, Warning or Error)
     */
    public void MessageBox(String message, int type);

    /**
     * Displays a message box with the supplied text and type
     * @param   message    The message to display in the box
     * @param   type       The type of message (Info, Warning or Error)
     * @param   picture    Picture to display in the MessageBox
     */
    public void MessageBox(String message, int type, String picture);

    /**
     * Check dvd-homevideo for runtime errors
     * @param   fileName    This is a log file to parse through, looking for
     *                      the keyword "Error"
     * @return   A boolean indicating if an error was found
     */
    public boolean ErrorCheck(String fileName);

    /**
     * Update the Status textbox
     * @param   typeColor   red, green, or grey
     * @param   typeUpdate  Values should be PASS, FAIL, Status
     */
    public void UpdateStatus(Color typeColor, String typeUpdate);

    /**
     * This method should be called every time the application exits normally.  This allows the
     * current session to be saved before quiting dvd-homevideo.
     */
    public void ExitDVDHomevideo();

    /**
     * This method retrieves user specific information from the
     * properties xml file in the users ~/.dvd-homevideo directory
     */
    public void ReadProjProperties();

    /**
     * This method stores user specific information in a properties xml file
     * in the users ~/.dvd-homevideo directory
     */
    public void WriteProjProperties();

    /**
     * Opens a previous dvd-homevideo session by reading an xml file
     * @param   xmlPath    Path to the xml file
     */
    public void ReadSession(String xmlPath);

    /**
     * Saves the state of all widgets on the GUI form in an xml file
     * in the users ~/.dvd-homevideo directory
     */
    public void WriteSession() throws IOException;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;

    private javax.swing.JButton btnOpen_Audio;

    private javax.swing.JButton btnOpen_OutputDir;

    private javax.swing.JButton btnOpen_Picture;

    private javax.swing.JButton btnOpen_TextFile;

    protected javax.swing.JButton btnPlay;

    private javax.swing.JButton btnStart;

    protected javax.swing.JCheckBox chkBurn;

    protected javax.swing.JCheckBox chkMenu;

    protected javax.swing.JCheckBox chkQuality;

    private javax.swing.JFileChooser fcMenuOpen;

    private javax.swing.JFileChooser fcOpen;

    private javax.swing.ButtonGroup grpAspectRatio;

    private javax.swing.ButtonGroup grpFormat;

    private javax.swing.ButtonGroup grpMenuAspectRatio;

    private javax.swing.ButtonGroup grpMenuFormat;

    private javax.swing.ButtonGroup grpMenuIgnore;

    private javax.swing.ButtonGroup grpMenuMode;

    private javax.swing.ButtonGroup grpQuality;

    private javax.swing.JLabel lblAspectRatio;

    private javax.swing.JLabel lblAudio;

    protected javax.swing.JLabel lblAuthor;

    protected javax.swing.JLabel lblAuthorProg;

    protected javax.swing.JLabel lblCapture;

    protected javax.swing.JLabel lblCaptureProg;

    protected javax.swing.JLabel lblConvert;

    protected javax.swing.JLabel lblConvertProg;

    private javax.swing.JLabel lblFormat;

    private javax.swing.JLabel lblMinutes;

    private javax.swing.JLabel lblOutputDir;

    private javax.swing.JLabel lblPicture;

    private javax.swing.JLabel lblSeconds;

    private javax.swing.JLabel lblTextFile;

    private javax.swing.JLabel lblTitle;

    private javax.swing.JMenuItem menuAbout;

    protected javax.swing.JMenuBar menuBarMain;

    private javax.swing.JMenuItem menuBug;

    protected javax.swing.JCheckBoxMenuItem menuChkThread;

    protected javax.swing.JMenuItem menuExit;

    protected javax.swing.JMenu menuFile;

    protected javax.swing.JMenu menuHelp;

    protected javax.swing.JMenuItem menuInternet;

    protected javax.swing.JMenuItem menuOpen;

    private javax.swing.JMenu menuProjProp;

    protected javax.swing.JMenuItem menuREADME;

    private javax.swing.JRadioButtonMenuItem menuRdNTSC;

    private javax.swing.JRadioButtonMenuItem menuRdPAL;

    private javax.swing.JRadioButtonMenuItem menuRd_16_9;

    private javax.swing.JRadioButtonMenuItem menuRd_4_3;

    protected javax.swing.JRadioButtonMenuItem menuRd_IgnoreCap;

    protected javax.swing.JRadioButtonMenuItem menuRd_IgnoreCapConv;

    protected javax.swing.JRadioButtonMenuItem menuRd_IgnoreCapConvMenu;

    protected javax.swing.JRadioButtonMenuItem menuRd_IgnoreNone;

    protected javax.swing.JMenuItem menuSave;

    protected javax.swing.JMenu menuTools;

    private javax.swing.JPanel pnlGUI;

    protected javax.swing.JProgressBar prgAuthor;

    protected javax.swing.JProgressBar prgCapture;

    protected javax.swing.JProgressBar prgConvert;

    protected javax.swing.JRadioButton rd16_9;

    protected javax.swing.JRadioButton rd4_3;

    protected javax.swing.JRadioButton rdAverage;

    protected javax.swing.JRadioButton rdGood;

    protected javax.swing.JRadioButton rdNTSC;

    protected javax.swing.JRadioButton rdPAL;

    protected javax.swing.JRadioButton rdSuper;

    private javax.swing.JScrollPane spTextArea;

    protected javax.swing.JSpinner spnMinutes;

    protected javax.swing.JSpinner spnSeconds;

    private javax.swing.JSeparator sprAspectRatio;

    private javax.swing.JSeparator sprCapConvert;

    private javax.swing.JSeparator sprIgnore;

    private javax.swing.JSeparator sprMenuAuthor;

    private javax.swing.JSeparator sprMenuFormatAspect;

    private javax.swing.JSeparator sprMenuThread;

    protected javax.swing.JSeparator sprOpen;

    protected javax.swing.JSeparator sprSave;

    protected javax.swing.JTextArea txtAreaOutput;

    protected javax.swing.JTextField txtAudio;

    protected javax.swing.JTextField txtOutputDir;

    protected javax.swing.JTextField txtPicture;

    protected javax.swing.JTextField txtStatus;

    protected javax.swing.JTextField txtTextFile;

    protected javax.swing.JTextField txtTitle;

    /* My GUI variable declarations */
    protected javax.swing.SpinnerNumberModel spnSecondsSize = new javax.swing.SpinnerNumberModel(0, 0, 59, 1);

    protected javax.swing.SpinnerNumberModel spnMinutesSize = new javax.swing.SpinnerNumberModel(0, 0, 64, 1);
}
