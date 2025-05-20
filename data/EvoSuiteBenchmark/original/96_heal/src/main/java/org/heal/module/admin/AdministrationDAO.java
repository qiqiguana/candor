package org.heal.module.admin;

import java.io.*;
import java.sql.SQLException;
import java.sql.*;
import javax.sql.DataSource;

import com.ora.jsp.sql.*;
import com.ora.jsp.sql.value.BooleanValue;
import org.heal.module.user.*;
import org.heal.util.*;
import org.heal.module.metadata.*;

/**
 * This DAO was created by Grace, its content came from original AdministrationServicesBean.java.
 * @author Seth Wright
 * @modify by Grace Yang
 * @version 0.1
 */

public class AdministrationDAO implements Serializable {
    //The accessor for database information
    private DataSource dataSource;
    CommonDAO cd = new CommonDAO();
    //The java bean/module used for getting the user information
    private UserRegistryBean userRegistry = null;
    //The file locator used to locate content to be deleted.
    private FileLocator fileLocator = null;

    /**
     * Sets the file locator to be used to translate metadata location
     * entries into file paths so that the deleteContent method can
     * actually delete the content.
     */
    public void setFileLocator(FileLocator newFileLocator) {
        fileLocator = newFileLocator;
    }

    /**
     * Returns the FileLocator being used by the Admin services.
     */
    public FileLocator getFileLocator() {
        return fileLocator;
    }

    /**
     * Sets the module used by the administration services to get user
     * information such as the list of admin users and the list of
     * subscribers for receiving HEAL notices.
     */
    public void setUserRegistry(UserRegistryBean newRegistry) {
        userRegistry = newRegistry;
    }

    /**
     * Sets the dataSource property value.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Performs a possible long running overall consistency and integrity
     * check of the data in teh library.  Returns a IntegrityReportBean
     * that contains statistics on corrupt files, orphaned files, content
     * without metadata, missing files, and duplicate files.
     */
    public void checkSystemIntegrity() {
        //XXX not implemented
    }

    /**
     * Marks a given metadata's entry as hidden, thus keeping it from showing
     * up in searches, etc.  Returns true if successful, false otherwise.
     */
    public boolean hideContent(String metadataId) {
        boolean success = false;
        try {
            updateMetadataBooleanProperty("Private", true, metadataId);
            success = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * Marks a given metadata's entry as visible, thus allowing it to show
     * up in searches, etc.  Returns true if successful, false otherwise.
     */
    public boolean makeContentVisible(String metadataId) {
        boolean success = false;
        try {
            updateMetadataBooleanProperty("Private", false, metadataId);
            success = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        return success;
    }

    /**
     * Removes all references to the metadata from the metadata tables, then
     * makes a new entry in the DeletedItems table.  Finally, the content
     * file itself is deleted.
     * Note:  All entries in the database associated with the metadata
     * will be deleted, this includes taxons related to taxon paths associated
     * with the metadata Id.  Also, copyright texts that are only referenced
     * from copyright entries associated with this metadata Id will also
     * be deleted.
     */
    public boolean deleteContent(String metadataId) {
        boolean success = false;
        MetadataBean metadata;
        ThumbnailBean thumbnail;
        String thumbnailLocation;
        File thumbnailFile;
        String contentLocation;
        File contentFile;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            metadata = cd.getMetadata(metadataId, conn);
            if(metadata != null) {
                contentLocation = metadata.getLocation();
                thumbnail = cd.getThumbnail(metadataId, conn);
                if(thumbnail != null) {
                    thumbnailLocation = thumbnail.getLocation();
                    if(thumbnailLocation != null) {
                        thumbnailFile = new File(fileLocator.getThumbnailFilePath(thumbnailLocation));
                        if(thumbnailFile != null) {
                            thumbnailFile.delete();
                        }
                    }
                }
                moveToDeletedItems(metadataId, "Content deleted.", conn);
                conn.commit();
                contentFile = new File(fileLocator.getContentFilePath(contentLocation));
                if(contentFile != null) {
                    success = contentFile.delete();
                } else {
                    success = true;
                }
            } else {
                success = true;
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.commit();
                    conn.close();
                } catch(Exception ex) {
                    //ignore for now
                }
            }
        }
        return success;
    }

    /**
     * Sets the permissions of the user in the database.
     * Returns true if the permissions were successfully set, false if
     * an error occured.
     */
    public boolean setPermissions(String userId, UserPermissionsBean permissions) {
        return userRegistry.setPermissions(userId, permissions);
    }

    /**
     * Given a property name and value and a metadata Id, updates
     * the metadata's table to reflect the given value.
     */
    public void updateMetadataBooleanProperty(String propertyName,
                                              boolean propertyValue,
                                              String metadataId)
            throws SQLException {
        updateMetadataValueProperty(propertyName, new BooleanValue(propertyValue), metadataId);
    }

    /**
     * Given a property name and value and a metadata Id, updates
     * the metadata's table to reflect the given value.
     */
    public void updateMetadataValueProperty(String propertyName, Value propertyValue, String metadataId)
            throws SQLException {
        // Save the metadata info from the database
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            cd.updateMetadataValueProperty(propertyName, propertyValue, metadataId, conn);
            conn.commit();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch(SQLException e) {
            } // Ignore
        }
    }

    /**
     * Given a metadataId, creates an entry for that metadata in the
     * DeletedItems table with information from the current Metadata
     * table and the provided comment.  The DeleteDate will be set
     * as the current date.
     * Returns true if no problems occured, false otherwise.
     */
    public boolean moveToDeletedItems(String metadataId, String comment, Connection conn)
            throws SQLException {
        boolean success = false;
        MetadataBean metadata = cd.getMetadata(metadataId, conn);
        cd.addToDeletedItems(metadata, comment, conn);
        success = cd.deleteMetadataReferences(metadataId, conn);
        cd.removeMetadataFromTable(metadataId, "Metadata", conn);
        return success;
    }
}
