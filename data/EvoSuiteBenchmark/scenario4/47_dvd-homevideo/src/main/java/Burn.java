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
     * Creates a new instance of Burn
     */
    public Burn() {
    }

    /**
     * Creates a new instance of Burn
     * @param   burnToDVD   boolean value used to determine whether or not to burn to a DVD
     *                      when dvd-homevideo completes
     */
    public Burn(boolean burnToDVD) {
    }

    /**
     * Burn Member Variables
     */
    private boolean m_Burn;

    private String m_growisofs = "growisofs -speed=4 -Z /dev/dvd -dvd-video temp/DVD/";

    private String m_BaseErr = "Burn Error - ";

    /**
     * Optionally burn to DVD when complete and no errors, uses growisofs
     * @param   DVD_GUI    This is the GUI object used to control the form
     * return   A boolean to determine if an error occurred in the function
     */
    public boolean BurnToDVD(GUI DVD_GUI);
}
