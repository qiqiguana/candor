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

    /**
     * Create a new file, or overwrite an existing file.
     * Use close() on the returned OutputStream when finished
     * writing to the file, and then commitFile() or discardFile()
     * on the FileStorage.
     *
     * @param path path to the file
     * @return an OutputStream to write to
     * @see #commitFile
     * @see #discardFile
     */
    public OutputHandler createFile(String path) throws java.io.IOException {
        String currentPath = path;
        String s = rootPath + path;
        int i = s.lastIndexOf('/');
        String dir = (i < 0) ? "." : s.substring(0, i);
        if (DEBUG)
            System.out.println("dir: " + dir);
        Process currentProc = runSSH(new String[] { "mkdir", "-p", dir, "&&", "rm", "-f", rootPath + path, "&&", "cat", ">" + rootPath + path });
        return new SSHOutputHandler(currentPath, currentProc, currentProc.getOutputStream());
    }
}
