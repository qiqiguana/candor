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

    private static final boolean DEBUG = false;

    private Socket control;

    private InputStream controlIn;

    private OutputStream controlOut;

    private String respString;

    private String lastPath = "";

    private int lastPathLen = 0;

    private void sendLine(String str) throws IOException;

    private String recvLine() throws EOFException, IOException;

    private int recvResponse() throws EOFException, IOException;

    /**
     * Connect to an FTP server and login.
     *
     * @param url  an URL specifying host, port, username and optionally
     *             an initial path to change to
     * @param password  the password to use for logging in
     *
     * @throws MalformedURLException  if there are any syntactic error in the URL
     * @throws UnknownHostException  if the hostname specified doesn't exist
     * @throws FTPAuthenticationException  if the password is wrong
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public FTPClient(String url, String password) throws MalformedURLException, UnknownHostException, FTPAuthenticationException, FTPException, IOException {
    }

    /**
     * Connect to an FTP server and login.
     *
     * @param host  the host to connect to, may be a domain name or IP address
     * @param port  the control channel port (default 21)
     * @param username  the username to use for logging in
     * @param password  the password to use for logging in
     * @param path  initial path, realtive to home directory unless starting with '/',
     *              may be <code>null</code> to use home directory
     *
     * @throws UnknownHostException  if the hostname specified doesn't exist
     * @throws FTPAuthenticationException  if the password is wrong
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public FTPClient(String host, int port, String username, String password, String path) throws UnknownHostException, FTPAuthenticationException, FTPException, IOException {
    }

    private void connect(String host, int port, String username, String password) throws UnknownHostException, FTPAuthenticationException, FTPException, IOException;

    private void initialDir(String path) throws FTPException, IOException;

    /**
     * Logout and disconnect from the FTP server.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close() throws IOException;

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

    /**
     * Change the current directory to the parent directory.
     *
     * @return true if successful, false otherwise
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    private boolean cdup() throws FTPException, IOException;

    /**
     * Create a new directory.
     *
     * @param dir  the directory to create
     *
     * @return true if successful, false otherwise
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    private boolean mkdir(String dir) throws FTPException, IOException;

    /**
     * Create a new file, or overwrite an existing file.
     * Will create directories as nessesary.
     *
     * @param pathname  path to the file
     *
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream store(String pathname) throws FTPException, IOException;

    /**
     * Create a new file, or overwrite an existing file.
     * Will create directories as nessesary.
     *
     * @param pathname  path to the file
     * @param last  close the FTPClient after transferring this file
     *
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream store(String pathname, boolean last) throws FTPException, IOException;

    /**
     * Create a new file, or append to an existing file.
     * Will create directories as nessesary.
     *
     * @param pathname  path to the file
     *
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream append(String pathname) throws FTPException, IOException;

    /**
     * Create a new file, or append to an existing file.
     * Will create directories as nessesary.
     *
     * @param pathname  path to the file
     * @param last  close the FTPClient after transferring this file
     *
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream append(String pathname, boolean last) throws FTPException, IOException;

    /**
     * Create a new file with an unique name.
     * Will create directories as nessesary.
     *
     * @param path  path to the file, must end with '/' or be empty
     *
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream storeUnique(String path) throws FTPException, IOException;

    /**
     * Create a new file with an unique name.
     * Will create directories as nessesary.
     *
     * @param path  path to the file, must end with '/' or be empty
     * @param last  close the FTPClient after transferring this file
     *
     * @return an OutputStream to write to, close() it when finished
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public OutputStream storeUnique(String path, boolean last) throws FTPException, IOException;

    private void changeDir(String path) throws FTPException, IOException;

    private OutputStream upload(String cmd, String filename, boolean last) throws FTPException, IOException;

    /**
     * Deletes a file.
     *
     * @param pathname  path to the file
     *
     * @return true if successful, false otherwise (file didn't exsist)
     * @throws FTPException if any FTP protocol error occurs
     * @throws IOException if any other IO error occurs
     */
    public boolean deleteFile(String pathname) throws FTPException, IOException;

    class FTPOutputStream extends OutputStream {

        private Socket data;

        private OutputStream out;

        private FTPClient ftp;

        FTPOutputStream(OutputStream out, Socket data, FTPClient ftp) {
            this.out = out;
            this.data = data;
            this.ftp = ftp;
        }

        public void write(int b) throws IOException {
            out.write(b);
        }

        public void write(byte[] b) throws IOException {
            out.write(b);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
        }

        public void flush() throws IOException {
            out.flush();
        }

        public void close() throws IOException {
            out.close();
            if (data != null) {
                data.close();
                data = null;
            }
            theLoop: while (true) {
                int resp = recvResponse();
                switch(resp) {
                    case 226:
                    case 250:
                        break;
                    case 425:
                    case 426:
                    case 451:
                    case 551:
                    case 552:
                        throw new FTPException("Error in file transfer (" + resp + ")");
                    case 421:
                        throw new FTPException("FTP server not avaliable (421)");
                    default:
                        throw new FTPException("Unexpected response from FTP server: " + respString);
                }
                break;
            }
            if (ftp != null)
                ftp.close();
        }
    }
}
