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

    /**
     * Create a new file, or overwrite an existing file.
     *
     * @param pathname path to the file
     */
    public OutputHandler createFile(String pathname) throws IOException {
        OutputStream os;
        try {
            os = ftp.store(pathname);
        } catch (IOException e) {
            if (DEBUG)
                System.out.println("FTP reconnecting: " + e.toString());
            try {
                ftp.close();
            } catch (IOException ignore) {
            }
            ftp = new FTPClient(url, password);
            os = ftp.store(pathname);
        }
        return new FTPOutputHandler(pathname, os);
    }
}

/**
 * An FTP client. See RFC-959.
 *
 * Pathnames must be specified using '/' for directory separator.
 * Passive mode will be used for all transfers.
 * <em>Not</em> thread-safe, i.e. you cannot start a new file while another one is in
 * progress.
 *
 * <strong>Note:</strong> This class will transmit password in clear text over
 * the network.
 */
public class FTPClient {

    /**
     * Create a new file, or overwrite an existing file.
     * Will create directories as nessesary.
     *
     * @param pathname path to the file
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream store(String pathname) throws FTPException, IOException;

    /**
     * Logout and disconnect from the FTP server.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close() throws IOException;
}
