package nu.staldal.lagoon.filestorage;

import java.io.*;
import nu.staldal.lagoon.core.*;

/**
 * A FileStorage using the local file system.
 */
public class LocalFileStorage implements FileStorage {

    public long fileLastModified(String path) // throws java.io.IOException
    {
        File file = root;
        int oldPos = 1;
        int pos;
        while (true) {
            pos = path.indexOf('/', oldPos);
            if (pos < 0)
                break;
            String comp = path.substring(oldPos, pos);
            file = new File(file, comp);
            if (!file.exists())
                return 0;
            oldPos = pos + 1;
        }
        file = new File(file, path.substring(oldPos));
        return file.lastModified();
    }
}
