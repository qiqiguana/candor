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
