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
    TreeSet contentSet = new TreeSet();
    String packageFileLocation = null;
    String packageURL = null;
    boolean packageModifiedSinceFileCreated = true;
    int packageFormat = -1;

    /**
     * Get a collection of content Ids.
     */
    public Collection getContentIds() {
        return contentSet;
    }

    /**
     * Returns true if the package has not been modified,
     * the package file location is not null, the
     * package URL is set, and the format of the
     * package is the same as that provided and
     * the package file exists on disk.
     */
    public boolean isPackageFileUpToDate(int format) {
        if(packageModifiedSinceFileCreated ||
                packageFileLocation == null ||
                packageURL == null ||
                format != packageFormat ||
                !(new File(packageFileLocation)).exists()) {
            return false;
        }
        return true;
    }

    /**
     * Returns the format for the package associated with this queue.
     * The value will be one of the static values defined in
     * DownloadServicesBean (ZIPFORMAT, GZIPFORMAT), or -1 if there
     * is no package associated with this queue.
     */
    public int getPackageFormat() {
        return packageFormat;
    }

    /**
     * Sets the format for the package associated with this queue.
     * This value should be one of the static values defined in
     * DownloadServicesBean (ZIPFORMAT, GZIPFORMAT), or -1 if there
     * is no package associated with this queue.
     */
    public void setPackageFormat(int newPackageFormat) {
        packageFormat = newPackageFormat;
    }

    /**
     * Returns the file path to be used to access the package for this queue.
     */
    public String getPackageFileLocation() {
        return packageFileLocation;
    }

    public void setPackageFileLocation(String newPackageFileLocation) {
        packageFileLocation = newPackageFileLocation;
    }

    /**
     * Returns the URL to be used to access the package for this queue.
     */
    public String getPackageURL() {
        return packageURL;
    }

    /**
     * Sets the url used to access this package file.
     */
    public void setPackageURL(String newPackageURL) {
        packageURL = newPackageURL;
    }

    /**
     * call this method right after the package file is created.
     * This is used to track whether or not the queue has been modified
     * since the last time the associated file was created.
     */
    public void setPackageFileCreated() {
        packageModifiedSinceFileCreated = false;
    }

    /**
     * Checks whether or not the queue has changed since the last time
     * the package was generated.  If the queue has had metadata entries
     * added or removed since the last time the package has been
     * generated or the package has not yet been generated yet, then this
     * method will return true
     * otherwise, it will return false
     */
    public boolean isPackageModified() {
        return packageModifiedSinceFileCreated;
    }

    /**
     * Adds a metadata id to the list of content to include in the package
     * generated for this queue.
     */
    public boolean addToQueue(String contentId) {
        packageModifiedSinceFileCreated = true;
        contentSet.add(contentId);
        return true;
    }

    /**
     * Removes a metadata id from the list of content to include in the package
     * generated for this queue.
     */
    public boolean removeFromQueue(String contentId) {
        packageModifiedSinceFileCreated = true;
        return contentSet.remove(contentId);
    }

    /**
     * Removes all metadata id from the list of content to include in the
     * package generated for this queue.
     */
    public boolean removeAllFromQueue() {
        packageModifiedSinceFileCreated = true;
        contentSet.clear();
        return true;
    }

    /**
     * Deletes all package file settings (last modified, location, url)
     * but leaves the queue of id's intact.
     * Returns true on success.
     */
    public boolean clearPackageFileSettings() {
        packageFileLocation = null;
        packageURL = null;
        packageModifiedSinceFileCreated = true;
        packageFormat = -1;
        return true;
    }

    /**
     * Returns true if the specified metadataId is already in the queue.
     * Otherwise, it returns false.
     */
    public boolean isQueuedAlready(String contentId) {
        return contentSet.contains(contentId);
    }

    /**
     * Returns the number of items queued for download.
     */
    public int getNumEntries() {
        return contentSet.size();
    }
}
