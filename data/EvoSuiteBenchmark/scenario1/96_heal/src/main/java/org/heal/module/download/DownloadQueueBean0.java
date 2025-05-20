package org.heal.module.download;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Stores a list of contentIds and a package file location and url.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class DownloadQueueBean implements Serializable {

    public boolean removeAllFromQueue() {
        packageModifiedSinceFileCreated = true;
        contentSet.clear();
        return true;
    }
}
