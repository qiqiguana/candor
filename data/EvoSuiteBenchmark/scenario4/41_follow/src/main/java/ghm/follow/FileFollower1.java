package ghm.follow;

import ghm.follow.io.OutputDestination;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instances of this class 'follow' a particular text file, assmebling that
 * file's characters into Strings and sending them to instances of
 * {@link OutputDestination}. The name and behavior of this class are inspired
 * by the '-f' (follow) flag of the UNIX command 'tail'.
 *
 * @see OutputDestination
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FileFollower {

    /**
     * Constructs a new FileFollower; invoking this constructor does
     * <em>not</em> cause the new object to begin following the supplied file.
     * In order to begin following, one must call {@link #start()}.
     *
     * @param file
     *            file to be followed
     * @param bufferSize
     *            number of chars to be read each time the file is accessed
     * @param latency
     *            each time a FileFollower's running thread encounters the end
     *            of the file in its stream, it will rest for this many
     *            milliseconds before checking to see if there are any more
     *            bytes in the file
     * @param initialOutputDestinations
     *            an initial array of OutputDestinations which will be used when
     *            printing the contents of the file (this array may be
     *            <tt>null</tt>)
     */
    public FileFollower(File file, int bufferSize, int latency, OutputDestination[] initialOutputDestinations) {
    }

    /**
     * Identical to {@link #FileFollower(File, int, int, OutputDestination[])},
     * except that a default buffer size (32,768 characters) and latency (1000
     * milliseconds) are used.
     *
     * @see #FileFollower(File, int, int, OutputDestination[])
     */
    public FileFollower(File file, OutputDestination[] initialOutputDestinations) {
    }

    /**
     * Cause this FileFollower to spawn a thread which will follow the file
     * supplied in the constructor and send its contents to all of the
     * FileFollower's OutputDestinations.<br>
     * <br>
     * If this FileFollower is running but paused, this method equates to
     * calling unpause().
     */
    public synchronized void start();

    public synchronized void pause();

    public synchronized void unpause();

    public synchronized void restart();

    /**
     * Cause this FileFollower to stop following the file supplied in the
     * constructor after it flushes the characters it's currently reading to all
     * its OutputDestinations.
     */
    public synchronized void stop();

    /**
     * Like {@link #stop()}, but this method will not exit until the thread
     * which is following the file has finished executing (i.e., stop
     * synchronously).
     */
    public synchronized void stopAndWait() throws InterruptedException;

    /**
     * Send the supplied string to all OutputDestinations
     *
     * @param s
     */
    private synchronized void print(String s);

    /**
     * Clear all OutputDestinations
     */
    private synchronized void clear();

    /**
     * Add another OutputDestination to which the followed file's contents
     * should be printed.
     *
     * @param outputDestination
     *            OutputDestination to be added
     */
    public boolean addOutputDestination(OutputDestination outputDestination);

    /**
     * Remove the supplied OutputDestination from the list of OutputDestinations
     * to which the followed file's contents should be printed.
     *
     * @param outputDestination
     *            OutputDestination to be removed
     */
    public boolean removeOutputDestination(OutputDestination outputDestination);

    /**
     * Returns the List which maintains all OutputDestinations for this
     * FileFollower.
     *
     * @return contains all OutputDestinations for this FileFollower
     */
    public List<OutputDestination> getOutputDestinations();

    /**
     * Returns the file which is being followed by this FileFollower
     *
     * @return file being followed
     */
    public File getFollowedFile();

    /**
     * Returns the following state of a file
     *
     * @return true if being followed, false if not being followed
     */
    public boolean isBeingFollowed();

    /**
     * Returns the pause state of the follower.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused();

    /**
     * Returns the size of the character buffer used to read characters from the
     * followed file. Each time the file is accessed, this buffer is filled.
     *
     * @return size of the character buffer
     */
    public int getBufferSize();

    /**
     * Sets the size of the character buffer used to read characters from the
     * followed file. Increasing buffer size will improve efficiency but
     * increase the amount of memory used by the FileFollower.<br>
     * <em>NOTE:</em> Setting this value will <em>not</em> cause a running
     * FileFollower to immediately begin reading characters into a buffer of the
     * newly specified size. You must stop & restart the FileFollower in order
     * for changes to take effect.
     *
     * @param bufferSize
     *            size of the character buffer
     */
    public void setBufferSize(int bufferSize);

    /**
     * Returns the time (in milliseconds) which a FileFollower spends sleeping
     * each time it encounters the end of the followed file.
     *
     * @return latency, in milliseconds
     */
    public int getLatency();

    /**
     * Sets the time (in milliseconds) which a FileFollower spends sleeping each
     * time it encounters the end of the followed file. Note that extremely low
     * latency values may cause thrashing between the FileFollower's running
     * thread and other threads in an application. A change in this value will
     * be reflected the next time the FileFollower's running thread sleeps.
     *
     * @param latency
     *            latency, in milliseconds
     */
    public void setLatency(int latency);

    protected int bufferSize;

    protected int latency;

    protected File file;

    protected List<OutputDestination> outputDestinations;

    protected boolean continueRunning;

    protected boolean needsRestart;

    protected Thread runnerThread;

    protected boolean paused;

    /**
     * Instances of this class are used to run a thread which follows a
     * FileFollower's file and sends prints its contents to OutputDestinations.
     * This class should only handle the gathering of data from the followed
     * file. Actually writing to the output destinations is handled by the outer
     * class (FileFollower).
     */
    class Runner implements Runnable {

        private Logger log = Logger.getLogger(Runner.class.getName());

        public void run() {
            log.finer("entering FileFollower.run()");
            while (continueRunning) {
                runAction();
            }
            log.finer("exiting FileFollower.run()");
        }

        protected void runAction() {
            try {
                clear();
                long fileSize = file.length();
                byte[] byteArray = new byte[bufferSize];
                int numBytesRead;
                long lastActivityTime = file.lastModified();
                // create some stream readers to handle the file
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                // start at the beginning of the file
                long startingPoint = 0;
                // if the file size is bigger than the buffer size, skip to the
                // end of the file if not performing a restart
                if (fileSize > bufferSize) {
                    startingPoint = fileSize - bufferSize;
                }
                // reset the restart flag
                needsRestart = false;
                log.finer("Starting point: " + startingPoint + "; Last activity: " + lastActivityTime);
                bis.skip(startingPoint);
                while (continueRunning && !needsRestart) {
                    if (!paused) {
                        lastActivityTime = System.currentTimeMillis();
                        numBytesRead = bis.read(byteArray, 0, byteArray.length);
                        boolean dataWasFound = (numBytesRead > 0);
                        log.finer("Bytes read: " + numBytesRead + "; dataWasFound: " + dataWasFound);
                        // if data was found, print it and log activity time
                        if (dataWasFound) {
                            String output = new String(byteArray, 0, numBytesRead);
                            // print the output to the listeners
                            print(output);
                        } else // no data found so check the file and restart if needed
                        {
                            // check if the file handle has become stale (file
                            // was modified, but no data was read).
                            boolean fileExists = file.exists();
                            // removed check for 0 length because a file could
                            // change by being cleared out
                            // && (file_.length() > 0);
                            boolean fileHasChanged = file.lastModified() > lastActivityTime;
                            if (fileExists && fileHasChanged) {
                                log.finer("Needs restart [fileExists=" + fileExists + "; fileHasChanged=" + fileHasChanged + "]");
                                needsRestart = true;
                            }
                        }
                        boolean allDataRead = (numBytesRead < byteArray.length);
                        if (allDataRead && !needsRestart) {
                            log.finer("Sleeping for " + latency + "ms [allDataRead:" + allDataRead + "; needsRestart:" + needsRestart + "]");
                            sleep();
                        }
                    } else {
                        log.finer("Runner paused.");
                        sleep();
                    }
                }
                log.finer("exiting Runner.runAction [continueRunning=" + continueRunning + "; needsRestart=" + needsRestart + "]");
                bis.close();
                fis.close();
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException while following file", e);
            }
        }

        private void sleep() {
            try {
                Thread.sleep(latency);
            } catch (InterruptedException e) {
                // Interrupt may be thrown manually by stop()
                log.finer("DIED IN MY SLEEP");
            }
        }
    }

    /**
     * Line separator, retrieved from System properties & stored statically.
     */
    protected static final String lineSeparator = System.getProperty("line.separator");
}
