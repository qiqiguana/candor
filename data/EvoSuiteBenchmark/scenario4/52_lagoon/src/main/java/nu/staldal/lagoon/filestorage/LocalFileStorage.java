package nu.staldal.lagoon.filestorage;

import java.io.*;
import nu.staldal.lagoon.core.*;

/**
 * A FileStorage using the local file system.
 */
public class LocalFileStorage implements FileStorage {

    private File root;

    /**
     * Default constructor.
     */
    public LocalFileStorage() {
    }

    public boolean needPassword();

    public boolean isReentrant();

    public void open(String loc, LagoonContext context, String password) throws java.io.IOException;

    /**
     * Close the file system and release any resources it holds.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close();

    /**
     * Check if a file exists and when it was last modified.
     *
     * @param path  path to the file
     *
     * @return  the time when the file was last modified,
     * or -1 if that information is not avaliable,
     * or 0 if the file doesn't exists.
     */
    public long fileLastModified(String path);

    /**
     * Create a new file, or overwrite an existing file.
     */
    public OutputHandler createFile(String path) throws java.io.IOException;

    /**
     * Deletes a file.
     * Does not signal any error if the file doesn't exist.
     *
     * @param path  path to the file
     */
    public void deleteFile(String path) throws java.io.IOException;

    static class LocalOutputHandler extends OutputHandler {

        private File currentFile;

        LocalOutputHandler(File currentFile, OutputStream out) {
            super(out);
            this.currentFile = currentFile;
        }

        public void commit() throws java.io.IOException {
            out.close();
        }

        public void discard() throws java.io.IOException {
            out.close();
            if (!currentFile.exists())
                return;
            if (currentFile.delete()) {
                return;
            } else {
                throw new IOException("Unable to delete file: " + currentFile);
            }
        }
    }
}
