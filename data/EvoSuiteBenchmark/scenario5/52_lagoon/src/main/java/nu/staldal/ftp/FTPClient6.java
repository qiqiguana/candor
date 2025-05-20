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

    /**
     * Deletes a file.
     *
     * @param pathname path to the file
     * @return true if successful, false otherwise (file didn't exsist)
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public boolean deleteFile(String pathname) throws FTPException, IOException {
        String path;
        String fn;
        int pos = pathname.lastIndexOf('/');
        path = pathname.substring(0, pos + 1);
        fn = pathname.substring(pos + 1);
        if (!path.equals(lastPath)) {
            // change directory
            for (int i = 0; i < lastPathLen; i++) if (!cdup())
                throw new FTPException("Unable to change to parent directory");
            lastPathLen = 0;
            int oldPos = 1;
            while (true) {
                pos = path.indexOf('/', oldPos);
                if (pos < 0)
                    break;
                lastPathLen++;
                String comp = path.substring(oldPos, pos);
                if (!chdir(comp)) {
                    // file doesn't exist
                    return false;
                }
                oldPos = pos + 1;
            }
        }
        lastPath = path;
        sendLine("DELE " + fn);
        int resp = recvResponse();
        switch(resp) {
            case 250:
                return true;
            case 550:
                return false;
            case 450:
                throw new FTPException("Unable to delete file: " + respString);
            case 421:
                throw new FTPException("FTP server not avaliable (421)");
            default:
                throw new FTPException("Unexpected response from FTP server: " + respString);
        }
    }

    /**
     * Change the current directory to the parent directory.
     *
     * @return true if successful, false otherwise
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    private boolean cdup() throws FTPException, IOException;

    /**
     * Change the current directory.
     *
     * @param dir  the directory to change into
     *
     * @return true if successful, false otherwise
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    private boolean chdir(String dir) throws FTPException, IOException;

    private void sendLine(String str) throws IOException;

    private int recvResponse() throws EOFException, IOException;
}
