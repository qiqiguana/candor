package nu.staldal.lagoon.filestorage;

import java.io.*;
import java.net.*;
import nu.staldal.lagoon.core.*;
import nu.staldal.ftp.*;

/**
 * A FileStorage which transfers files to a remote site using FTP.
 *
 * <strong>Note:</strong> This class will transmit password in clear text over
 * the network.
 */
public class FTPFileStorage extends RemoteFileStorage {

    private static final boolean DEBUG = false;

    private FTPClient ftp;

    private String url;

    private String password;

    /**
     * Default constructor.
     */
    public FTPFileStorage() {
    }

    public boolean needPassword();

    public boolean isReentrant();

    public void open(String url, LagoonContext context, String password) throws MalformedURLException, UnknownHostException, FTPException, IOException, AuthenticationException;

    /**
     * Close the file system and release any resources it holds.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close() throws IOException;

    /**
     * Create a new file, or overwrite an existing file.
     *
     * @param pathname  path to the file
     */
    public OutputHandler createFile(String pathname) throws IOException;

    /**
     * Deletes a file.
     * Does not signal any error if the file doesn't exist.
     *
     * @param pathname  path to the file
     */
    public void deleteFile(String pathname) throws java.io.IOException;

    class FTPOutputHandler extends OutputHandler {

        private String currentPathname;

        FTPOutputHandler(String currentPathname, OutputStream out) {
            super(out);
            this.currentPathname = currentPathname;
        }

        public void commit() throws java.io.IOException {
            out.close();
            fileModified(currentPathname);
        }

        public void discard() throws java.io.IOException {
            try {
                commit();
            } catch (FTPException e) {
                // ignore exception
            }
            ftp.deleteFile(currentPathname);
        }
    }
}
