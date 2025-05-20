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
    public OutputHandler createFile(String pathname) throws IOException;
}
