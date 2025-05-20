package nu.staldal.ftp;

import java.io.*;
import java.net.*;

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

    public OutputStream append(String pathname, boolean last) throws FTPException, IOException {
        int pos = pathname.lastIndexOf('/');
        String path = pathname.substring(0, pos + 1);
        String filename = pathname.substring(pos + 1);
        changeDir(path);
        return upload("APPE", filename, last);
    }
}
