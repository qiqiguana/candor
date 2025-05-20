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
    public boolean BurnToDVD(GUI DVD_GUI);
}
