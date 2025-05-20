package org.heal.util;

import org.heal.module.user.UserBean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class translates URLs to file system paths and vice versa.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class FileLocator implements Serializable {

    public static final String DEFAULT_UPLOAD_DIRECTORY = "upload";
    public static final String DEFAULT_THUMBNAIL_DIRECTORY = "thumbnails";
    public static final String DEFAULT_CONTENT_DIRECTORY = "content";
    public static final String DEFAULT_PACKAGE_DIRECTORY = "package";

    private String uploadDirectory = DEFAULT_UPLOAD_DIRECTORY;
    private File uploadDirectoryFile = null;
    private String uploadFilePath = null;
    private String uploadURL = null;
    private String serverBaseURL = null;
    private int serverBaseURLLength = -1;
    private String contentDirectory = DEFAULT_CONTENT_DIRECTORY;
    private File contentDirectoryFile = null;
    private String contentFilePath = null;
    private String contentURL = null;
    private String baseFilePath = null;
    private int baseFilePathLength = -1;
    private String packageDirectory = DEFAULT_PACKAGE_DIRECTORY;
    private File packageDirectoryFile = null;
    private String packageFilePath = null;
    private String packageURL = null;
    private String thumbnailDirectory = DEFAULT_THUMBNAIL_DIRECTORY;
    private File thumbnailDirectoryFile = null;
    private String thumbnailFilePath = null;
    private String thumbnailURL = null;
    private long maxUploadSize = 512 * 1024 * 1024; //512 Megabytes

    /**
     * Returns the maximum size allowed for uploaded files.
     * This value is in bytes.
     */
    public long getMaxUploadSize() {
        return maxUploadSize;
    }

    /**
     * Sets the maximum size allowed for uploaded files.
     * This value is in bytes.
     */
    public void setMaxUploadSize(long newMaxUploadSize) {
        maxUploadSize = newMaxUploadSize;
    }

    /**
     * Returns the upload directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the upload directory.  The URL for the upload
     * directory will then be: http://<baseURL>/<uploadDirectory>/
     * and the path to the upload directory will reside at:
     * <baseFilePath>/<uploadDirectory>/
     */
    public String getUploadDirectory() {
        return uploadDirectory;
    }

    /**
     * Determines the directory to store content below the base directory
     * used in the various sections of the application (i.e. content or upload)
     * This plus the filename should be used to create the location setting
     * in the Metadata table.
     * The directory is created by combining information from the given
     * user with the given format (i.e. illustration/photograph)
     * The returned directory is of the format:
     * <user id>/<contentFormat>
     * If user is null, or contentformat is null (or empty), returns null.
     */
    public String getLocationDirectory(UserBean user, String contentFormat) {
        if(user == null || contentFormat == null || contentFormat.length() < 1) {
            return null;
        }
        return user.getUserId() + File.separator + contentFormat;
    }

    /**
     * Returns the thumbnail directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the thumbnail directory.  The URL for the thumbnail
     * directory will then be: http://<baseURL>/<thumbnailDirectory>/
     * and the path to the thumbnail directory will reside at:
     * <baseFilePath>/<thumbnailDirectory>/
     */
    public String getThumbnailDirectory() {
        return thumbnailDirectory;
    }

    /**
     * Returns the server base URL property setting.
     * This is the base url on the server where all content
     * (uploaded, cataloged, approved - all content in the system).
     * This baseurl + the upload directory should point to the
     * upload directory in the webserver.
     */
    public String getServerBaseURL() {
        return serverBaseURL;
    }

    /**
     * Returns the base file path property setting.
     * This is the base directory on the filesystem where all content
     * (uploaded, cataloged, approved - all content in the system).
     * This filepath + the upload directory should point to the
     * upload directory in the filesystem.
     */
    public String getBaseFilePath() {
        return baseFilePath;
    }

    /**
     * Returns the content directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the content directory.  The URL for the content
     * directory will then be: http://<baseURL>/<contentDirectory>/
     * and the path to the content directory will reside at:
     * <baseFilePath>/<contentDirectory>/
     */
    public String getContentDirectory() {
        return contentDirectory;
    }

    /**
     * Returns the package directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the package directory.  The URL for the package
     * directory will then be: http://<baseURL>/<packageDirectory>/
     * and the path to the package directory will reside at:
     * <baseFilePath>/<packageDirectory>/
     */
    public String getPackageDirectory() {
        return packageDirectory;
    }

    /**
     * Sets the thumbnail directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the thumbnail directory.  The URL for the thumbnail
     * directory will then be: http://<baseURL>/<thumbnailDirectory>/
     * and the path to the thumbnail directory will reside at:
     * <baseFilePath>/<thumbnailDirectory>/
     * Be sure to call generateFullDirectories before using this class
     * after changing this setting.
     */
    public void setThumbnailDirectory(String newThumbnail) {
        thumbnailDirectory = newThumbnail;
    }

    /**
     * Sets the upload directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the upload directory.  The URL for the upload
     * directory will then be: http://<baseURL>/<uploadDirectory>/
     * and the path to the upload directory will reside at:
     * <baseFilePath>/<uploadDirectory>/
     * Be sure to call generateFullDirectories before using this class
     * after changing this setting.
     */
    public void setUploadDirectory(String newUpload) {
        uploadDirectory = newUpload;
    }

    /**
     * Sets the server base URL property setting.
     * This is the base url on the server where all content
     * (uploaded, cataloged, approved - all content in the system).
     * This baseurl + the upload directory should point to the
     * upload directory in the webserver.
     * Be sure to call generateFullDirectories before using this class
     * after changing this setting.
     */
    public void setServerBaseURL(String newBaseURL) {
        serverBaseURL = newBaseURL;
        if(serverBaseURL != null) {
            serverBaseURLLength = serverBaseURL.length();
        } else {
            serverBaseURLLength = -1;
        }
    }

    /**
     * Sets the base file path property setting.
     * This is the base directory on the filesystem where all content
     * (uploaded, cataloged, approved - all content in the system).
     * This filepath + the upload directory should point to the
     * upload directory in the filesystem.
     * Be sure to call generateFullDirectories before using this class
     * after changing this setting.
     */
    public void setBaseFilePath(String newFilePath) {
        baseFilePath = newFilePath;
        if(baseFilePath != null) {
            baseFilePathLength = baseFilePath.length();
        } else {
            baseFilePathLength = -1;
        }
    }

    /**
     * Sets the content directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the content directory.  The URL for the content
     * directory will then be: http://<baseURL>/<contentDirectory>/
     * and the path to the content directory will reside at:
     * <baseFilePath>/<contentDirectory>/
     * Be sure to call generateFullDirectories before using this class
     * after changing this setting.
     */
    public void setContentDirectory(String newContentDirectory) {
        contentDirectory = newContentDirectory;
    }

    /**
     * Sets the package directory property setting.
     * This is the path that is tacked on after either the baseURL
     * or the baseFilePath to get the logical or translated locations
     * of the files in the package directory.  The URL for the package
     * directory will then be: http://<baseURL>/<packageDirectory>/
     * and the path to the package directory will reside at:
     * <baseFilePath>/<packageDirectory>/
     * Be sure to call generateFullDirectories before using this class
     * after changing this setting.
     */
    public void setPackageDirectory(String newPackage) {
        packageDirectory = newPackage;
    }

    /**
     * Given a URL, calculates the file system path to use to access
     * the file.  The calculation is made using the base URL and
     * base file path properties.
     */
    public String getFilePathFromURL(String url) {
        if(url == null) {
            return null;
        }
        int index;
        String temp, postfix;
        String result = null;
        index = url.indexOf(serverBaseURL);
        if(index != -1) {
            postfix = url.substring(index + serverBaseURLLength);
            temp = baseFilePath + File.separator + postfix;
            result = temp.replace('/', File.separatorChar);
        }
        return result;
    }

    /**
     * Given a file path, calculates the url to use to access
     * the file via the web server.  The calculation is made using the
     * base URL and base file path properties.
     */
    public String getURLFromFilePath(String path) {
        if(path == null) {
            return null;
        }
        int index;
        String temp, postfix;
        String result = null;
        index = path.indexOf(baseFilePath);
        if(index != -1) {
            postfix = path.substring(index + baseFilePathLength);
            temp = serverBaseURL + File.separator + postfix;
            result = temp.replace(File.separatorChar, '/');
        }
        return result;
    }

    /**
     * Generates the content filepath and url and the upload filepath and url
     * properties.  This method is called whenever the contentDirectory
     * uploadDirectory, baseFilePath, or serverBaseURL properties are changed.
     * This method should be called once all of the settings have been made
     * and the FileLocator is ready for use.
     */
    public void generateFullDirectories() {
        if(baseFilePath != null) {
            if(contentDirectory != null) {
                contentFilePath = baseFilePath + File.separator + contentDirectory;
                contentDirectoryFile = new File(contentFilePath);
                if(!contentDirectoryFile.exists()) {
                    contentDirectoryFile.mkdirs();
                }
            }
            if(uploadDirectory != null) {
                uploadFilePath = baseFilePath + File.separator + uploadDirectory;
                uploadDirectoryFile = new File(uploadFilePath);
                if(!uploadDirectoryFile.exists()) {
                    uploadDirectoryFile.mkdirs();
                }
            }
            if(thumbnailDirectory != null) {
                thumbnailFilePath = baseFilePath + File.separator
                        + thumbnailDirectory;
                thumbnailDirectoryFile = new File(thumbnailFilePath);
                if(!thumbnailDirectoryFile.exists()) {
                    thumbnailDirectoryFile.mkdirs();
                }
            }
            if(packageDirectory != null) {
                packageFilePath = baseFilePath + File.separator + packageDirectory;
                packageDirectoryFile = new File(packageFilePath);
                if(!packageDirectoryFile.exists()) {
                    packageDirectoryFile.mkdirs();
                }
            }
        }
        if(serverBaseURL != null) {
            if(contentDirectory != null) {
                contentURL = serverBaseURL + '/' + contentDirectory + '/';
            }
            if(uploadDirectory != null) {
                uploadURL = serverBaseURL + '/' + uploadDirectory + '/';
            }
            if(thumbnailDirectory != null) {
                thumbnailURL = serverBaseURL + '/' + thumbnailDirectory + '/';
            }
            if(packageDirectory != null) {
                packageURL = serverBaseURL + '/' + packageDirectory + '/';
            }
        }
    }

    /**
     * Returns the file path generated from the uploadDirectory and base
     * file path properties.
     */
    public String getUploadFilePath() {
        return uploadFilePath;
    }

    /**
     * Returns the file path result of the package directory concatenated
     * with the provided source location.
     */
    public String getUploadFilePath(String sourceLocation) {
        String convertedSource = sourceLocation.replace('/', File.separatorChar);
        File fullPath = new File(uploadFilePath, convertedSource);
        String retval = null;
        try {
            retval = fullPath.getCanonicalPath();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return retval;
    }

    /**
     * Returns the url result of the upload directory concatenated
     * with the provided source location.
     */
    public String getUploadURL(String sourceLocation) {
        if(sourceLocation.startsWith("http://")) /*same mod as getContentURL*/ {
            return sourceLocation;
        } else {
            return uploadURL + sourceLocation.replaceAll("\\\\", "/");
        }
    }

    /**
     * Returns the url generated from the uploadDirectory and server base
     * url properties.
     */
    public String getUploadURL() {
        return uploadURL;
    }

    /**
     * Returns the file path generated from the thumbnailDirectory and base
     * file path properties.
     */
    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    /**
     * Returns the file path result of the thumbnail directory concatenated
     * with the provided source location.
     */
    public String getThumbnailFilePath(String sourceLocation) {
        String convertedSource = sourceLocation.replace('/', File.separatorChar);
        File fullPath = new File(thumbnailFilePath, convertedSource);
        String retval = null;
        try {
            retval = fullPath.getCanonicalPath();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return retval;
    }

    /**
     * Returns the url result of the thumbnail directory concatenated
     * with the provided source location.
     */
    public String getThumbnailURL(String sourceLocation) {
        if(sourceLocation.startsWith("http:")) {
            return sourceLocation;
        } else {
            return thumbnailURL + sourceLocation;
        }
    }

    /**
     * Returns the url generated from the thumbnailDirectory and server base
     * url properties.
     */
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    /**
     * Returns the file path generated from the contentDirectory and base
     * file path properties.
     */
    public String getContentFilePath() {
        return contentFilePath;
    }

    /**
     * Returns the file path result of the content directory concatenated
     * with the provided source location.
     */
    public String getContentFilePath(String sourceLocation) {
        if(sourceLocation.toLowerCase().startsWith(getContentURL().toLowerCase())) {
            sourceLocation = sourceLocation.substring(getContentURL().length());
        }
        String convertedSource = sourceLocation.replace('/', File.separatorChar);
        File fullPath = new File(contentFilePath, convertedSource);
        String retval = null;
        try {
            retval = fullPath.getCanonicalPath();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        return retval;
    }

    /**
     * Returns the url result of the content directory concatenated
     * with the provided source location.
     */
    /* mod by JV for http:// starting items */
    public String getContentURL(String sourceLocation) {
        if(sourceLocation.startsWith("http://")) {
            return sourceLocation;
        } else {
            return contentURL + sourceLocation;
        }
    }

    /**
     * Returns the url generated from the contentDirectory and server base
     * url properties.
     */
    public String getContentURL() {
        return contentURL;
    }

    /**
     * Returns the file path generated from the packageDirectory and base
     * file path properties.
     */
    public String getPackageFilePath() {
        return packageFilePath;
    }

    /**
     * Returns the directory storing packages.
     */
    public File getPackageDirectoryFile() {
        return packageDirectoryFile;
    }

    /**
     * Returns the url generated from the packageDirectory and server base
     * url properties.
     */
    public String getPackageURL() {
        return packageURL;
    }

    /**
     * Returns a string consisting of only the path of a given location that
     * falls below the preset content, package, and upload directories.  This
     * method works for both URLs and file paths.  This method relies upon
     * the base url, base file, content, package, and upload path settings.
     * Given a base url of 'http://www.healcentral.org/'
     * a file url of 'd:\apache\htdocs\'
     * a content path of 'content'
     * a package path of 'package'
     * and an upload path of 'upload'
     * The following results will be given:
     * getRelativePath(c:\apache\content\brain\picture.jpg) = brain\picture.jpg
     * getRelativePath(http://www.healcentral.org/content/brain/picture.jpg) =
     * brain/picture.jpg
     * <p/>
     * The approach to determining the relative path is to look for first the
     * content directory, then the upload directory, then the package
     * directory to see if any of them appear in the location string
     * (using the String.indexOf() method).
     * <p/>
     * If the parameter is null, or the content, upload, or package paths are
     * not foud, then null is returned.
     */
    public String getRelativePath(String location) {
        int index = -1;
        char separator;
        String result = null;
        int locationLen;
        if(location != null) {
            locationLen = location.length();
            //check each directory type
            if((contentDirectory != null &&
                    (index = location.indexOf(File.separator + contentDirectory + File.separator)) != -1)) {
                //move the index to past the location of the directory
                index += contentDirectory.length();
            } else if((uploadDirectory != null &&
                    (index = location.indexOf(File.separator + uploadDirectory + File.separator)) != -1)) {
                index += uploadDirectory.length();
            } else if((thumbnailDirectory != null &&
                    (index = location.indexOf(File.separator + thumbnailDirectory + File.separator)) != -1)) {
                index += thumbnailDirectory.length();
            } else if((packageDirectory != null &&
                    (index = location.indexOf(File.separator + packageDirectory + File.separator)) != -1)) {
                index += packageDirectory.length();
            }

            /* if we found the location, see if we need to skip a
             * file separator or URL separator
             */
            if(index != -1) {
                separator = location.charAt(index);
                if(separator == File.separatorChar ||
                        separator == '/') {
                    //we found a separator, so skip over it
                    index++;
                }
                //if the index isn't past the end, get the rest of the string
                if(index < locationLen) {
                    result = location.substring(index);
                }
            }
        }
        return result;
    }

    /**
     * Moves the given file from the upload directory to the content
     * directory.  There is no error checking for conflicts.
     */
    public boolean moveFromUploadToContent(String location)
            throws IOException {
        boolean success = false;
        String filepath = convertLocationToFilePath(location);
        if(filepath != null) {
            File from = new File(uploadFilePath, filepath);
            if(from.exists()) {
                File to = new File(contentFilePath, filepath);
                success = from.renameTo(to);
            }
        }
        return success;
    }

    /**
     * Just like the getRelativePath method, but replaces any occurences of
     * '/' with File.separatorChar.
     */
    public String getRelativeFilePath(String location) {
        String retval = getRelativePath(location);
        if(retval != null) {
            retval.replace('/', File.separatorChar);
        }
        return retval;
    }

    /**
     * Just like the getRelativePath method, but replaces any occurences of
     * File.separatorChar with '/'.
     */
    public String getRelativeURLPath(String location) {
        String retval = getRelativePath(location);
        if(retval != null) {
            retval.replace(File.separatorChar, '/');
        }
        return retval;
    }

    /**
     * Given a "location" setting (see the Location field of the
     * Metadata table) it replaces all of the path separators to
     * be those of the local operating system, allowing the
     * application to get a path to the content file.
     * example: location = "2/Photograph/brain.jpg", returns
     * "2\Photograph\brain.jpg" on Windows,
     * "2/Photograph/brain.jpg" on Solaris
     * Returns null if the location parameter is null.
     */
    public String convertLocationToFilePath(String location) {
        if(location == null) {
            return null;
        }
        String retval = new String(location);
        return retval.replace('/', File.separatorChar);
    }

    /**
     * Given a file path setting it replaces all of the path
     * separators to be '/' as is standardized in the
     * Location field of the Metadata table in the database.
     * The path field should contain the local operating systems
     * File.separatorChar as the separator between directories.
     * This method should only be used for relative file paths
     * of the format below, and the path shouldn't contain
     * the upload, content, package, or thumbnail directories,
     * but rather the relative path below those.
     * <p/>
     * example: (Windows)path = "2\Photograph\brain.jpg", or
     * "2/Photograph/brain.jpg" on Solaris
     * returns
     * "2/Photograph/brain.jpg"
     * Returns null if the filepath parameter is null.
     */
    public String convertFilePathToLocation(String location) {
        if(location == null) {
            return null;
        }
        String retval = new String(location);
        return retval.replace(File.separatorChar, '/');
    }

    /**
     * Given a location directory (i.e. one generated via the
     * getLocationDirectory method), checks all possible places a file by
     * the name of locationdirectory/name could be.  If a redundancy is
     * detected, it will separate the filename and extension information
     * in name and begin adding numbers to the name until no redundancy is
     * found.  For example, if location directory is "1/audio" and the name
     * is "sound.avi" and the file <uploaddir>/1/audio/sound.avi is found or
     * the file <contentdir>/1/audio/sound.avi is found, then the method will
     * try against <uploaddir>/1/audio/sound1.avi and
     * <contentdir>/1/audio/sound1.avi, sound2.avi, sound3.avi, etc. until no
     * conflict is found.  When no conflict is found the returned String
     * will be of the form: 1/audio/sound4.avi
     * <p/>
     * Checks files in both the upload and content directories.
     * Also creates the parent directory of the file if it does not exist.
     */
    public String getUniqueFileLocation(String locationDirectory, String name) {
        String filePath = locationDirectory + File.separator + name;
        //try quick and dirty first...
        File uploadFile = new File(uploadFilePath, filePath);
        if(!uploadFile.exists()) {
            File contentFile = new File(contentFilePath, filePath);
            if(!contentFile.exists()) {
                File uploadParent = uploadFile.getParentFile();
                if(!uploadParent.exists()) {
                    uploadParent.mkdirs();
                }
                File contentParent = contentFile.getParentFile();
                if(!contentParent.exists()) {
                    contentParent.mkdirs();
                }
                return filePath;
            }
        }
        //if we get here, one of the files existed
        int counter = 2; //if we have a redundancy, start at 2(e.g. brain2.jpg)
        int extensionIndex = name.lastIndexOf('.');
        //extension will contain the period also: e.g. ".jpg"
        String extension = "";
        String fileName = name;
        if(extensionIndex > 0) {
            extension = name.substring(extensionIndex);
            fileName = name.substring(0, extensionIndex);
        }
        while(true) {
            filePath = locationDirectory +
                    File.separator +
                    fileName +
                    counter +
                    extension;
            uploadFile = new File(uploadFilePath, filePath);
            if(!uploadFile.exists()) {
                File contentFile = new File(contentFilePath, filePath);
                if(!contentFile.exists()) {
                    File uploadParent = uploadFile.getParentFile();
                    if(!uploadParent.exists()) {
                        uploadParent.mkdirs();
                    }
                    File contentParent = contentFile.getParentFile();
                    if(!contentParent.exists()) {
                        contentParent.mkdirs();
                    }
                    return filePath;
                }
            }
            //if we get here we had a collision, so we increment the counter.
            counter++;
        }
    }

    public void createParentDir(String filepath) {

    }
}
