package org.heal.module.download;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.HealMetadataXMLConverter;
import org.heal.module.metadata.MetadataDAO;
import org.heal.util.FileLocator;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This DAO was created by Grace, its content came from original DownloadServicesBean.java.
 *
 * @author Seth Wright
 * @version 0.1
 * @modify by Grace Yang
 */

public class DownloadDAO implements Serializable {
    public static final int ZIPTYPE = 0;
    public static final int GZIPTYPE = 1;
    public static final String ZIPFORMAT = "application/zip";
    public static final String GZIPFORMAT = "application/x-gzip";
    public static final String ZIPEXTENSION = ".zip";
    public static final String GZIPEXTENSION = ".gz";
    public static final String HEALPREFIX = "heal";

    //The database to use in order to access the metadata info.
    private DataSource dataSource;
    private MetadataDAO metadatadao;
    /* The file locator to use to determine where the package
     * files should be placed.
     */
    private FileLocator fileLocator = null;

    /**
     * Sets the dataSource property value and metadataDAO.
     * add by Grace
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Sets the utility class to use in determining where the package
     * files should be placed.
     */
    public void setMetadataDAO(MetadataDAO metaDao) {
        metadatadao = metaDao;
    }

    /**
     * Sets the utility class to use in determining where the package
     * files should be placed.
     */
    public void setFileLocator(FileLocator newFileLocator) {
        fileLocator = newFileLocator;
    }

    /**
     * Creates an IMS package of the given file format containing an ims
     * manifest and the content specified by the download queue.
     * Returns the url to be used to access the created package.
     * Currently the only supported format is ZIP.
     * Accepted fileFormat strings: "application/zip" and "application/x-gzip"
     * or you can use the constants (recommended)
     * DownloadServicesBean.ZIPFORMAT and DownloadServicesBean.GZIPFORMAT
     * This method also sets the download queue's package file location and
     * url.  Yes, the return value is slightly redundant.
     */
    /**
     * Creates an IMS package of the given file format containing an ims
     * manifest and the content specified by the download queue.
     * Returns the url to be used to access the created package.
     * Currently the only supported format is ZIP.
     * Accepted fileFormat strings: "application/zip" and "application/x-gzip"
     * or you can use the constants (recommended)
     * DownloadServicesBean.ZIPFORMAT and DownloadServicesBean.GZIPFORMAT
     * This method also sets the download queue's package file location and
     * url.  Yes, the return value is slightly redundant.
     */
    public String createPackage(DownloadQueueBean queue, String fileFormat) throws IOException, SQLException {
        int fileType;
        if(queue == null || fileFormat == null) {
            throw new SQLException("Missing DownloadQueue or FileFormat");
        }
        if(fileFormat.equals(ZIPFORMAT)) {
            fileType = ZIPTYPE;
        } else if(fileFormat.equals(GZIPFORMAT)) {
            fileType = GZIPTYPE;
        } else {
            throw new SQLException("Unrecognized package type");
        }
        if(queue.isPackageFileUpToDate(fileType)) {
            return queue.getPackageURL();
        }
        //if we are going to create a new file, then we need to delete
        //the old one.
        String packageFileStr = queue.getPackageFileLocation();
        if(packageFileStr != null) {
            File aFile = new File(packageFileStr);
            if(aFile.exists()) {
                aFile.delete();
            }
            queue.clearPackageFileSettings();
        }
        File packageFile = getNewPackageFile(queue, fileType);
        if(packageFile == null) {
            throw new SQLException("Could not create package file");
        }
        CompleteMetadataBean[] metadata = getCompleteMetadata(queue);
        if(metadata == null) {
            return null;
        }
        FileOutputStream packageStream = new FileOutputStream(packageFile);
        ZipOutputStream zipStream = new ZipOutputStream(packageStream);
        zipStream.setMethod(ZipOutputStream.DEFLATED);
        zipStream.setLevel(Deflater.BEST_COMPRESSION);

        ZipEntry manifestEntry = new ZipEntry("imsmanifest.xml");
        manifestEntry.setTime(System.currentTimeMillis());
        zipStream.putNextEntry(manifestEntry);

        HealMetadataXMLConverter.getManifestIMSXML(metadata, fileLocator, zipStream);
        zipStream.closeEntry();

        String metaLocation;
        String metaFile;
        for(int i = 0; i < metadata.length; i++) {
            metaLocation = metadata[i].getMetadata().getLocation();
            metaFile = fileLocator.getContentFilePath(metaLocation);
            addToZip(zipStream, metaFile);
        }
        zipStream.close();
        queue.setPackageFileCreated();
        return queue.getPackageURL();
    }

    /**
     * Gathers all of the metadata ids from the download queue, looks them
     * up in the database and returns an array of the associated metadata
     * beans.  If a metadata entry is not found for a content id, then that
     * that metadata will simply not be added to the return array.
     */
    private CompleteMetadataBean[] getCompleteMetadata(DownloadQueueBean queue) throws SQLException {
        Collection ids = queue.getContentIds();
        Iterator idsIterator = ids.iterator();
        String contentId;
        CompleteMetadataBean metadata;
        ArrayList metaList = new ArrayList();
        while(idsIterator.hasNext()) {
            contentId = (String)idsIterator.next();
            metadata = metadatadao.getCompleteMetadata(contentId);
            if(metadata != null) {
                metaList.add(metadata);
            }
        }
        int numMetadata = metaList.size();
        CompleteMetadataBean[] array = new CompleteMetadataBean[numMetadata];
        metaList.toArray(array);
        return array;
    }

    /**
     * Deletes the given package.  The package location is determined from
     * the url of the package using the FileLocator.
     */
    public boolean deletePackage(String packageURL) {
        if(packageURL == null) {
            return false;
        }
        boolean success = false;
        String packageFileStr = fileLocator.getFilePathFromURL(packageURL);
        if(packageFileStr != null) {
            File packageFile = new File(packageFileStr);
            success = packageFile.delete();
        }
        return success;
    }

    /**
     * Returns a file extension for a given format.  The formats are defined
     * at the beginning of this class.  (zip, gzip) If the format is not
     * recognized, we return null.
     */
    private String getFileSuffix(int format) {
        switch(format) {
            case ZIPTYPE:
                return ZIPEXTENSION;
            case GZIPTYPE:
                return GZIPEXTENSION;
            default:
                return null;
        }
    }

    /**
     * Returns a new temporary name for a package file based on the
     * provided format.  Right now, the only supported format is ZIP and GZIP,
     * so all returned filenames will be of the form: <packagepath>/name.zip
     * or <packagepath>/name.gz
     * Returns null on an error.
     * NOTE: This method also sets the downloadQueue's package file location
     * and URL.
     */
    private File getNewPackageFile(DownloadQueueBean queue, int format) throws IOException {
        File packageDirectory = fileLocator.getPackageDirectoryFile();
        if(packageDirectory == null) {
            throw new IOException("Could not get File representation of package directory from FileLocator.");
        }
        String suffix = getFileSuffix(format);
        File packageFile = File.createTempFile(HEALPREFIX, suffix, packageDirectory);
        String path = packageFile.getCanonicalPath();
        queue.setPackageFileLocation(path);
        queue.setPackageFormat(format);
        queue.setPackageURL(fileLocator.getPackageURL() + packageFile.getName());
        return packageFile;
    }

    /**
     * Adds a file or directory (and its contents) to a zip file.  This
     * method sets each file's entry in the zip file to have a filepath
     * relative to the zip file rather than full path entries.  Also, the
     * zip entry's time value is set to the last modified date of the
     * file.  addToZip works recursively on a directory to include all of the
     * files under that directory.
     */
    private void addToZip(ZipOutputStream zos, String fileOrFolderPathToBeZip) throws IOException {
        File fd = new File(fileOrFolderPathToBeZip);
        if(fd.exists() && fd.isFile()) {
            // Create a file input stream and a buffered input stream.
            FileInputStream fis = new FileInputStream(fd);
            BufferedInputStream bis = new BufferedInputStream(fis);
            // Create a Zip Entry and put it into the archive(no data yet).
            // uses the relative path of the entry.
            ZipEntry fileEntry = new ZipEntry(fileLocator.getRelativePath(fd.getPath()));
            fileEntry.setTime(fd.lastModified());
            zos.putNextEntry(fileEntry);
            byte[] data = new byte[1024];
            int byteCount;
            while((byteCount = bis.read(data, 0, 1024)) > -1) {
                zos.write(data, 0, byteCount);
            }
            try {
                fis.close();
            } catch(IOException ex) {
                //we just want it to close, so ignore the error
            }
        } else if(fd.exists() && fd.isDirectory()) {
            File[] fileList = fd.listFiles();
            // Loop through File array and display.
            for(int i = 0; i < fileList.length; i++) {
                addToZip(zos, fileList[i].getPath());
            } // for loop
        }
    }
}
