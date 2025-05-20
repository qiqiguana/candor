import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StringIndexOutOfBoundsException;
import java.util.StringTokenizer;

/**
 * @author Shane Santner
 * This class is used to transcode the video captured
 * from the digital camcorder into mpeg4, the DVD compatible
 * format.  It explicitly calls the transcode command
 * after meticulously preparing the options that can be passed
 * to transcode.
 *
 * TODO - Break mplex out into its own class.
 *        Figure out how to calculate remaining time even if
 *        video was not captured from the camcorder during the
 *        current session.
 *        Need to handle input, output and error streams
 *        more appropriatly.
 */
public class Convert implements Runnable {

    /**
     * Creates a new instance of Convert based on the video
     * format and aspect ratio specified.
     * @param   format      This specifies either PAL or NTSC
     * @param   aspectRatio This can be either 4:3 or 16:9
     * @param   DVD_GUI     This is the GUI object used to control the form
     */
    public Convert(String format, String aspectRatio, GUI DVD_GUI) {
    }

    /**
     * Overloaded Constructor - accounts for quality being selected
     * @param   quality     The quality of the video compression
     * @param   format      This specifies either PAL or NTSC
     * @param   aspectRatio This can be either 4:3 or 16:9
     * @param   DVD_GUI     This is the GUI object used to control the form
     */
    public Convert(int quality, String format, String aspectRatio, GUI DVD_GUI) {
    }

    /**
     * Convert Member Variables
     */
    private int m_Quality;

    private double m_fps;

    private String m_Format;

    private String m_AspectRatio;

    private String m_flags;

    private String m_bitrate = "8500";

    protected String[] video_files;

    private GUI m_GUI;

    private Thread m_Thread;

    private boolean m_Error;

    private int thread_track;

    private String m_BaseErr = "Transcoding Error - ";

    private String m_transcode = "transcode -i dv/inp -m test1.ac3 -o test1 -w bitr -x dv,dv" + " -F flags -y mpeg2enc,raw -N 0x2000 -b 256 --encode_fields b" + " -E 48000,16,2 -J resample" + " --export_prof format --export_fps frames/s --export_asr aspectRatio" + " -j 0,8,0,8 --print_status 30";

    private String mplex = "mplex -f 8 -V -o inp.vob inp.m2v inp.ac3";

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
     * Encodes dv files to mpeg using transcode, then uses mplex to combine
     * the .ac3 audio and .m2v video files into a DVD compatible .vob file
     */
    public void Transcode();

    /**
     * Outputs a text file to be used by mpeg2enc for encoding
     * @param   m_GUI This is the GUI object used to control the form
     */
    public void matrix();
}
