import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

/**
 * @author Shane Santner
 * This class create the DVD filesystem that will be burned
 * to disc.
 *
 * TODO - Need to handle input, output and error streams
 *        more appropriatly.
 */
public class Author {

    /**
     * Creates a new instance of Author
     */
    public Author() {
    }

    /* Author Member Variables */
    private String m_dvdauthor = "dvdauthor -x vmgm.xml";

    private String m_BaseErr = "Authoring Error - ";

    /**
     * Create a DVD filestructure necessary to play in dvd player using dvdauthor
     * @param   DVD_GUI This is the GUI object used to control the form
     * @return  A boolean to determine if an error occurred in the function
     */
    public boolean CreateDVDFileStructure(GUI DVD_GUI);
}
