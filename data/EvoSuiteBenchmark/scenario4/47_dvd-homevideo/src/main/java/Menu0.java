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

    /**
     * Creates a default instance of Menu
     */
    public Menu() {
    }

    /**
     * Creates a new instance of Menu with the title, text file path and PAL/NTSC format
     * passed as parameters.
     * @param   title           The title of the DVD
     * @param   TextFilePath    The path to the title file
     * @param   isPAL           Is this a PAL or NTSC DVD
     */
    public Menu(String title, String TextFilePath, boolean isPAL, GUI DVD_GUI) {
    }

    /**
     * Creates a new instance of Menu with the title, text file path, PAL/NTSC format,
     * menu picture path and audio path passed as parameters.
     * @param   title           The title of the DVD
     * @param   picPath         The path to the background picture of the DVD Menu
     * @param   audioPath       The path to the background audio of the DVD Menu
     * @param   TextFilePath    The path to the title file
     * @param   isPAL           Is this a PAL or NTSC DVD
     */
    public Menu(String title, String picPath, String audioPath, String TextFilePath, boolean isPAL, GUI DVD_GUI) {
    }

    /**
     * Menu Member Variables
     */
    private String strTitle;

    private String strPicPath;

    private String strAudioPath;

    private String strTextFilePath;

    private String dvd_menu = "dvd-menu";

    private String[] titles = new String[50];

    private boolean pal_menu;

    private String baseErr = "Menu Error - ";

    private String[] video_files;

    private GUI m_GUI;

    private Thread m_Thread;

    private boolean m_Error;

    /**
     * Used to instantiate a new thread and to perform error checking.
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean init();

    /**
     * Implements the run() method of the Runnable interface.  Makes multi-threading
     * possible.
     */
    public void run();

    /**
     * Creates DVD menu using optional picture and music by calling dvd-menu
     * @param   m_GUI This is the GUI object used to control the form
     * @return  A boolean to determine if an error occurred in the function
     */
    public void DVDMainMenu();

    /**
     * Creates the xml file to pass to dvdauthor
     * @param   m_GUI This is the GUI object used to control the form
     */
    public boolean createXML();
}
