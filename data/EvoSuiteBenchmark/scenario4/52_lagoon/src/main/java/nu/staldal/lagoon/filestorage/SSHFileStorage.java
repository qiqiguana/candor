package nu.staldal.lagoon.filestorage;

import java.io.*;
import java.net.MalformedURLException;
import nu.staldal.lagoon.core.FileStorage;
import nu.staldal.lagoon.core.OutputHandler;
import nu.staldal.lagoon.core.RemoteFileStorage;
import nu.staldal.lagoon.core.LagoonContext;

/**
 * A FileStorage which transfers files to a remote site using SSH.
 */
public class SSHFileStorage extends RemoteFileStorage {

    private static final boolean DEBUG = false;

    private String host;

    private int port;

    private String username;

    private String rootPath;

    private Runtime rt;

    private Process runSSH(String[] command) throws IOException;

    /**
     * Default constructor.
     */
    public SSHFileStorage() {
    }

    public boolean needPassword();

    public boolean isReentrant();

    public void open(String url, LagoonContext context, String passoword) throws MalformedURLException, IOException;

    /**
     * Close the file system and release any resources it holds.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close() throws IOException;

    /**
     * Create a new file, or overwrite an existing file.
     * Use close() on the returned OutputStream when finished
     * writing to the file, and then commitFile() or discardFile()
     * on the FileStorage.
     *
     * @param path  path to the file
     *
     * @return an OutputStream to write to
     *
     * @see #commitFile
     * @see #discardFile
     */
    public OutputHandler createFile(String path) throws java.io.IOException;

    /**
     * Deletes a file.
     * Does not signal any error if the file doesn't exist.
     *
     * @param path  path to the file
     */
    public void deleteFile(String path) throws java.io.IOException;

    class SSHOutputHandler extends OutputHandler {

        private String currentPath;

        private Process currentProc;

        SSHOutputHandler(String currentPath, Process currentProc, OutputStream out) {
            super(out);
            this.currentPath = currentPath;
            this.currentProc = currentProc;
        }

        public void commit() throws java.io.IOException {
            out.close();
            try {
                currentProc.waitFor();
            } catch (InterruptedException e) {
            }
            fileModified(currentPath);
        }

        public void discard() throws java.io.IOException {
            out.close();
            try {
                currentProc.waitFor();
            } catch (InterruptedException e) {
            }
            Process proc = runSSH(new String[] { "rm", "-f", rootPath + currentPath });
            proc.getOutputStream().close();
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
            }
        }
    }
}
