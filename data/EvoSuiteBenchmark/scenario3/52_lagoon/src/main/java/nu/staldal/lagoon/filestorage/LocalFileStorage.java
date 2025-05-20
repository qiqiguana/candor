package nu.staldal.lagoon.filestorage;

import java.io.*;
import nu.staldal.lagoon.core.*;

/**
 * A FileStorage using the local file system.
 */
public class LocalFileStorage implements FileStorage {

    /**
     * Check if a file exists and when it was last modified.
     *
     * @param path path to the file
     * @return the time when the file was last modified,
     * or -1 if that information is not avaliable,
     * or 0 if the file doesn't exists.
     */
    public long fileLastModified(String path);
}
