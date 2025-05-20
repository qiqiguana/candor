package ghm.follow.gui;

import ghm.follow.FileFollower;
import ghm.follow.io.JTextComponentDestination;
import ghm.follow.io.OutputDestination;
import ghm.follow.search.SearchableTextPane;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * A component which allows one to view a text file to which information is being asynchronously
 * appended.
 *
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FileFollowingPane extends JScrollPane {

    private Logger log = Logger.getLogger(FileFollowingPane.class.getName());

    /**
     * FileFollower used to print to this component
     */
    protected FileFollower fileFollower;

    /**
     * Text area into which followed file's contents are printed
     */
    protected SearchableTextPane textArea;

    /**
     * OutputDestination used w/FileFollower
     */
    protected JTextComponentDestination destination;

    /**
     * @param file
     *            text file to be followed
     * @param bufferSize
     *            size of the character buffer inside the FileFollower used to follow the supplied
     *            file
     * @param latency
     *            latency of the FileFollower used to follow the supplied file
     */
    public FileFollowingPane(File file, int bufferSize, int latency, boolean autoPositionCaret, Font font, int tabSize) {
    }

    /**
     * Returns the text area to which the followed file's contents are being printed.
     *
     * @return text area containing followed file's contents
     */
    public SearchableTextPane getTextPane();

    /**
     * Returns whether caret is automatically repositioned to the end of the text area when text is
     * appended to the followed file
     *
     * @return whether caret is automatically repositioned on append
     */
    public boolean autoPositionCaret();

    /**
     * Sets whether caret is automatically repositioned to the end of the text area when text is
     * appended to the followed file
     *
     * @param value
     *            whether caret is automatically repositioned on append
     */
    public void setAutoPositionCaret(boolean value);

    /**
     * Returns the FileFollower which is being used to print information in this component.
     *
     * @return FileFollower used by this component
     */
    public FileFollower getFileFollower();

    /**
     * Convenience method; equivalent to calling getFileFollower().getFollowedFile()
     */
    public File getFollowedFile();

    /**
     * Convenience method; equivalent to calling getFileFollower().start()
     */
    public void startFollowing();

    /**
     * Convenience method; equivalent to calling getFileFollower().stop()
     */
    public void stopFollowing();

    /**
     * Convenience method; equivalent to calling getFileFollower().restart()
     */
    public void restartFollowing();

    /**
     * Convenience method; equivalent to calling getFileFollower().pause()
     */
    public void pauseFollowing();

    /**
     * Convenience method; equivalent to calling getFileFollower().unpause()
     */
    public void unpauseFollowing();

    /**
     * Convenience method; equivalent to calling getFileFollower().isPaused()
     *
     * @return
     */
    public boolean isFollowingPaused();

    /**
     * Convenience method; equivalent to calling getFileFollower().stopAndWait()
     */
    public void stopFollowingAndWait() throws InterruptedException;

    /**
     * Convenience method; equivalent to called getFileFollower().isBeingFollowed()
     *
     * @return
     */
    public boolean isFollowing();

    /**
     * Clears the contents of this FileFollowingPane synchronously.
     */
    public void clear() throws IOException;
}
