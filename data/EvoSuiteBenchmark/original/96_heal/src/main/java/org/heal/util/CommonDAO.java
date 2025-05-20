package org.heal.util;

import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedTypeException;
import com.ora.jsp.sql.Value;
import com.ora.jsp.sql.value.BooleanValue;
import com.ora.jsp.sql.value.IntValue;
import com.ora.jsp.sql.value.LongValue;
import com.ora.jsp.sql.value.StringValue;
import com.ora.jsp.sql.value.TimestampValue;
import com.ora.jsp.sql.value.DateValue;

import org.heal.module.metadata.ContextURLBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.module.metadata.CopyrightBean;
import org.heal.module.metadata.CopyrightHolderBean;
import org.heal.module.metadata.CopyrightTextBean;
import org.heal.module.metadata.DiseaseDiagnosisBean;
import org.heal.module.metadata.FormatBean;
import org.heal.module.metadata.KeywordBean;
import org.heal.module.metadata.MetadataBean;
import org.heal.module.metadata.MetametadataContributorBean;
import org.heal.module.metadata.MetametadataIdentifierBean;
import org.heal.module.metadata.RelationBean;
import org.heal.module.metadata.RequirementBean;
import org.heal.module.metadata.SourceCollectionBean;
import org.heal.module.metadata.TargetUserGroupBean;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.module.metadata.ThumbnailBean;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Grace
 * @version 0.1
 */

public class CommonDAO implements Serializable {
    public CommonDAO() {
    }

    private DataSource dataSource;

    private static final String THUMBNAILINSERTSQL = "INSERT INTO Thumbnails (MetadataId, Location, FileWidth, FileHeight) VALUES(?, ?, ?, ?)";
    private static final String THUMBNAILUPDATESQL = "UPDATE Thumbnails SET MetadataId = ?, Location= ?, FileWidth= ?, FileHeight = ? WHERE ThumbnailID = ?";
    private static final String COPYRIGHTINSERTSQL = "INSERT INTO Copyrights (MetadataID, CopyrightTextID) VALUES(?, ?)";
    private static final String COPYRIGHTUPDATESQL = "UPDATE Copyrights SET MetadataID = ?, CopyrightTextID = ? WHERE CopyrightID = ?";

    private static final String COPYRIGHTTEXTINSERTSQL = "INSERT INTO CopyrightTexts (CopyrightText, Cost, CopyrightAndOtherRestriction) VALUES(?, ?, ?)";
    private static final String COPYRIGHTTEXTUPDATESQL = "UPDATE CopyrightTexts SET CopyrightText = ?, Cost = ?, CopyrightAndOtherRestriction = ? WHERE CopyrightTextID = ?";
    private static final String COPYRIGHTTEXTLOOKUPIDSQL = "SELECT CopyrightTextID FROM CopyrightTexts WHERE CopyrightText = ?";
    private static final String DISEASEDIAGNOSISINSERTSQL = "INSERT INTO DiseaseDiagnoses (MetadataID, DiseaseDiagnosis) VALUES(?, ?)";
    private static final String DISEASEDIAGNOSISUPDATESQL = "UPDATE DiseaseDiagnoses SET MetadataID = ?, DiseaseDiagnosis = ? WHERE DiseaseDiagnosisID = ?";
    private static final String COPYRIGHTHOLDERINSERTSQL = "INSERT INTO CopyrightHolders (MetadataID, vCardID) VALUES(?, ?)";
    private static final String COPYRIGHTHOLDERUPDATESQL = "UPDATE CopyrightHolders SET MetadataID = ?, vCardID = ? WHERE CopyrightHolderID = ?";
    private static final String KEYWORDINSERTSQL = "INSERT INTO Keywords (MetadataID, Keyword) VALUES(?, ?)";
    private static final String KEYWORDUPDATESQL = "UPDATE Keywords SET MetadataID = ?, Keyword = ? WHERE KeywordID = ?";

    private static final String FORMATINSERTSQL = "INSERT INTO Formats (MetadataID, Format) VALUES(?, ?)";
    private static final String FORMATUPDATESQL = "UPDATE Formats SET MetadataID = ?, Format = ? WHERE FormatID = ?";


    private static final String TAXONPATHINSERTSQL = "INSERT INTO TaxonPaths (MetadataID, Source, Purpose, Description, Keyword) VALUES(?, ?, ?, ?, ?)";
  //  private static final String TAXONPATHINSERTSQL = "INSERT INTO TaxonPaths (MetadataID, Source) VALUES(?, ?)";
    private static final String TAXONINSERTSQL = "INSERT INTO Taxons (TaxonPathID, ID, Entry) VALUES(?, ?, ?)";
    private static final String TAXONUPDATESQL = "UPDATE Taxons SET TaxonPathID = ?, ID = ?, Entry = ? WHERE TaxonID = ?";


    private static final String CONTEXTURLINSERTSQL = "INSERT INTO ContextURLs (MetadataID, ContextURL, ContextURLDescription) VALUES(?, ?, ?)";
    private static final String CONTEXTURLUPDATESQL = "UPDATE ContextURLs SET MetadataID = ?, ContextURL = ?, ContextURLDescription = ? WHERE ContextURLID = ?";

    private static final String REQUIREMENTINSERTSQL = "INSERT INTO Requirements (MetadataID, RequirementType, RequirementName, OtherPlatformRequirements, Duration, Description) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String REQUIREMENTUPDATESQL = "UPDATE Requirements SET MetadataID = ?, RequirementType = ?, RequirementName = ?, OtherPlatformRequirements = ?, Duration = ?, Description = ? WHERE RequirementID = ?";

    private static final String CONTRIBUTORINSERTSQL = "INSERT INTO Contributors (MetadataID, Role, vCardID, ContributeDate, ContributeDateDescription, Version, Status) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String CONTRIBUTORUPDATESQL = "UPDATE Contributors SET MetadataID = ?, Role = ?, vCardID = ?, ContributeDate = ?, ContributeDateDescription = ?, Version = ?, Status = ? WHERE ContributorID = ?";

    private static final String RELATIONINSERTSQL = "INSERT INTO Relations (MetadataID, Resource, Kind, Description, Catalogue, Entry) VALUES(?, ?, ?, ?, ?, ?)";  
    private static final String RELATIONUPDATESQL = "UPDATE Relations SET MetadataID = ?, Resource = ?, Kind = ?, Description = ?, Catalogue = ?, Entry = ? WHERE RelationID = ?";

    private static final String TARGETUSERGROUPINSERTSQL = "INSERT INTO TargetUserGroups (MetadataId, TargetUserGroup) VALUES (?, ?)";

    private static final String DELETEDITEMSINSERTSQL = "INSERT INTO DeletedItems (GlobalID, FileName, Title, Location, ContributeUserID, ContributeDate, DeleteDate, Comments) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";


    /**
     * Sets the dataSource property value.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Given a property name and value and a metadata Id, updates
     * the metadata's table to reflect the given value.
     */
    public void updateMetadataBooleanProperty(String propertyName, boolean propertyValue, String metadataId, Connection conn)
            throws SQLException {
        updateMetadataValueProperty(propertyName, new BooleanValue(propertyValue), metadataId, conn);
    }

    /**
     * Given a property name and value and a metadata Id, updates
     * the metadata's table to reflect the given value.
     */
    public void updateMetadataValueProperty(String propertyName, Value propertyValue, String metadataId, Connection conn)
            throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE Metadata SET ").append(propertyName).append(" = ? WHERE ");
        sql.append("MetadataID = ?");
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(propertyValue);
        values.addElement(new StringValue(metadataId));
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Given a property name and value and a metadata Id, updates
     * the metadata's table to reflect the given value.
     */
    public void updateMetadataStringProperty(String propertyName,
                                             String propertyValue,
                                             String metadataId,
                                             Connection conn)
            throws SQLException {
        updateMetadataValueProperty(propertyName,
                new StringValue(propertyValue),
                metadataId,
                conn);
    }

    /**
     * Given a property name and value and a metadata Id, updates
     * the metadata's table to reflect the given value.
     */
    public void updateMetadataTimestampProperty(String propertyName,
                                                Timestamp propertyValue,
                                                String metadataId,
                                                Connection conn)
            throws SQLException {
        updateMetadataValueProperty(propertyName,
                new TimestampValue(propertyValue),
                metadataId,
                conn);
    }

    /**
     * Returns a Vector of Rows with all of the requested columns from the
     * requested table that have the specified metadataId in their
     * MetadataID column, or null if no matches are found.
     */
    public Vector getMetadataProperties(String metadataId,
                                        String columnNames,
                                        String tableName,
                                        Connection conn)
            throws SQLException {

        if(metadataId == null) {
            return null;
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ").append(columnNames).append(" FROM ").append(tableName).append(" ")
                .append("WHERE MetadataID = ?");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(metadataId));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }

        if(rows == null || rows.size() == 0) {
            // Metadata not found
            return null;
        }
        return rows;
    }

    public TimestampValue getTimestampValue(Date date) {
        TimestampValue ret;
        if(null != date) {
            ret = new TimestampValue(new Timestamp(date.getTime()));
        } else {
            ret = new TimestampValue(null);
        }
        return ret;
    }

    public IntValue getIntValue(String intString) {
        int parsedInt = 0;
        try {
            parsedInt = Integer.parseInt(intString);
        } catch(NumberFormatException ex) {
            //XXX logging?
        }
        return new IntValue(parsedInt);
    }

    public LongValue getLongValue(String longString) {
        long parsedLong = 0;
        try {
            parsedLong = Long.parseLong(longString);
        } catch(NumberFormatException e) {

        }
        return new LongValue(parsedLong);
    }


    /**
     * Gets all rows from a given table.  The return value is a Vector
     * of com.ora.jsp.sql.Row object or null if an error occurs.
     */
    public Vector getAllRows(String tableName) throws SQLException {
        if(tableName == null) {
            return null;
        }
        Connection conn = dataSource.getConnection();
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue("SELECT * FROM " + tableName);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }
        return rows;
    }

    /**
     * Returns a MetadataBean initialized with the information
     * found in the database specified by the given metadataId,
     * or null if the metadataId is not found in the database.
     */
    public MetadataBean getMetadata(String metadataId, Connection conn)
            throws SQLException {

        // Get the metadata info from the database
        Row metadataRow = getMetadataProperty(metadataId, "Metadata.*, Publications.Name AS PubName, Publications.PublicationDate AS PubDate", "Metadata LEFT OUTER JOIN Publications ON Metadata.PublicationId = Publications.PublicationId", conn);
        if(metadataRow == null) {
            return null;
        }

        MetadataBean metadataInfo = new MetadataBean();
        try {
            metadataInfo.setMetadataId(metadataRow.getString("MetadataID"));
            metadataInfo.setGlobalId(metadataRow.getString("GlobalID"));
            metadataInfo.setFileName(metadataRow.getString("FileName"));
            metadataInfo.setFileSize(metadataRow.getString("FileSize"));
            metadataInfo.setTitle(metadataRow.getString("Title"));
            metadataInfo.setLocation(metadataRow.getString("Location"));
            metadataInfo.setSourceCollection(metadataRow.getString("SourceCollection"));
            metadataInfo.setLearningResourceType(metadataRow.getString("LearningResourceType"));
            metadataInfo.setContributeUserId(metadataRow.getString("ContributeUserID"));
            metadataInfo.setContributeDate(DateTools.parse(metadataRow.getString("ContributeDate")));
            metadataInfo.setAnnotated(metadataRow.getString("Annotated"));
            metadataInfo.setInappropriate(metadataRow.getString("Inappropriate"));
            metadataInfo.setArchived(metadataRow.getString("Archived"));
            metadataInfo.setPrivate(metadataRow.getString("Private"));
            metadataInfo.setDescription(metadataRow.getString("Description"));
            metadataInfo.setSpecimenType(metadataRow.getString("SpecimenType"));
            metadataInfo.setRadiographType(metadataRow.getString("RadiographType"));
            metadataInfo.setOrientation(metadataRow.getString("Orientation"));
            metadataInfo.setMagnification(metadataRow.getString("Magnification"));
            metadataInfo.setClinicalHistory(metadataRow.getString("ClinicalHistory"));
            metadataInfo.setFileWidth(metadataRow.getString("FileWidth"));
            metadataInfo.setFileHeight(metadataRow.getString("FileHeight"));
            metadataInfo.setDuration(metadataRow.getString("Duration"));
            metadataInfo.setApproveDate(DateTools.parse(metadataRow.getString("ApproveDate")));
            metadataInfo.setCatalogDate(DateTools.parse(metadataRow.getString("CatalogDate")));
            metadataInfo.setRejectDate(DateTools.parse(metadataRow.getString("RejectDate")));
            metadataInfo.setCreationDate(DateTools.parse(metadataRow.getString("CreationDate")));
            metadataInfo.setPublicationName(metadataRow.getString("PubName"));
            metadataInfo.setPublicationDate(DateTools.parse(metadataRow.getString("PubDate")));

            // For some reason a null integer gets returned from getString as "0"
            // so this ensures the publicationId is populated as null correctly
            final String publicationId = metadataRow.getString("PublicationId");
            metadataInfo.setPublicationId("0".equals(publicationId) ? null : publicationId);
            metadataInfo.setLanguageType(metadataRow.getString("LanguageType"));
        } catch(NoSuchColumnException nsce) {
            throw new SQLException(nsce.toString());
        }
        return metadataInfo;
    }

    /**
     * Returns a Row with all information about the specified
     * metadata or null if the metadata is not found.
     */
    public Row getMetadataProperty(String metadataId, String columnNames, String tableName, Connection conn)
            throws SQLException {
        Vector results = getMetadataProperties(metadataId, columnNames, tableName, conn);
        if(results != null) {
            return (Row)results.firstElement();
        } else {
            return null;
        }
    }

    /**
     * Removes all taxons and taxon paths associated with the given metadataId.
     */
    public void removeTaxonPaths(String metadataId, Connection conn) throws SQLException {
        if(metadataId == null || conn == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM Taxons WHERE TaxonPathID in (SELECT TaxonPathID FROM TaxonPaths WHERE MetadataID = ?)");
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(metadataId));
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
        sql.setLength(0);
        sql.append("DELETE FROM TaxonPaths WHERE MetadataID = ?");
        sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Removes all copyright entries with the given metadataId
     * from the Copyrights table.
     */
    public void removeCopyrights(String metadataId, Connection conn) throws SQLException {
        if(metadataId == null || conn == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM Copyrights WHERE MetadataID = ?");

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(metadataId));
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
        sql.setLength(0);
        //clean up the copyright texts
        sql.append("DELETE FROM CopyrightTexts WHERE CopyrightTextID NOT IN (SELECT CopyrightTextID FROM Copyrights)");
        sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        values = new Vector();
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Removes all entries in a table that have a given metadataId.
     */
    public void removeMetadataFromTable(String metadataId, String tableName, Connection conn) throws SQLException {
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE FROM ").append(tableName).append(" ")
                .append("WHERE MetadataID = ?");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(metadataId));
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Deletes the metadata entry from the metadata table.
     * Returns true if no problems occured, false otherwise.
     */
    public boolean deleteMetadata(String metadataId) {
        boolean success = false;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            removeMetadataFromTable(metadataId, "Metadata", conn);
            success = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(conn != null) {
                    conn.setAutoCommit(true);
                    conn.commit();
                    conn.close();
                }
            } catch(SQLException ex2) {
                //ignore
            }
        }
        return success;
    }

    public String getMetadataPropertyAsString(String metadataId, String columnName, String tableName, Connection conn)
            throws SQLException {
        Row row = getMetadataProperty(metadataId, columnName, tableName, conn);
        if(row != null) {
            try {
                return row.getString(columnName);
            } catch(NoSuchColumnException ex) {
                throw new SQLException(ex.toString());
            }
        } else {
            return null;
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
        MetadataBean metadata = getMetadata(metadataId, conn);
        addToDeletedItems(metadata, comment, conn);
        success = deleteMetadataReferences(metadataId, conn);
        removeMetadataFromTable(metadataId, "Metadata", conn);
        return success;
    }

    /**
     * Takes information from the metadata bean and stores it in the
     * deleted items table, with the addition of the provided comment.
     */
    public void addToDeletedItems(MetadataBean metadata, String comment, Connection conn)
            throws SQLException {
        if(metadata == null) {
            throw new SQLException("Invalid metadata provided.");
        }
        Timestamp deleteDate = new Timestamp(System.currentTimeMillis());
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(DELETEDITEMSINSERTSQL);
        Vector values = new Vector();
        values.addElement(new StringValue(metadata.getGlobalId()));
        values.addElement(new StringValue(metadata.getFileName()));
        values.addElement(new StringValue(metadata.getTitle()));
        values.addElement(new StringValue(metadata.getLocation()));
        values.addElement(getIntValue(metadata.getContributeUserId()));
        values.addElement(getTimestampValue(metadata.getContributeDate()));
        values.addElement(new TimestampValue(deleteDate));
        values.addElement(new StringValue(comment));
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Removes all references to the given metadataId, EXCEPT for the main
     * entry in the Metadata table.  All other references are removed from
     * the other tables (Formats, Keywords, Requirements, etc).
     * Also deleted are all taxons associated with taxonpaths associated with
     * this metadata reference.  Also, all copyrights associated with the
     * metadata are deleted.  If the copyright to be deleted contains the only
     * reference to the associated copyright text, then the copyright text
     * is also deleted.
     * Returns true if no problems occured, false otherwise.
     */
    public boolean deleteMetadataReferences(String metadataId, Connection conn)
            throws SQLException {
        //first do the easy removals...
        removeMetadataFromTable(metadataId, "DiseaseDiagnoses", conn);
        removeMetadataFromTable(metadataId, "Formats", conn);
        removeMetadataFromTable(metadataId, "Keywords", conn);
        removeMetadataFromTable(metadataId, "Relations", conn);
        removeMetadataFromTable(metadataId, "Contributors", conn);
        removeMetadataFromTable(metadataId, "Requirements", conn);
        removeMetadataFromTable(metadataId, "ContextURLs", conn);
        removeMetadataFromTable(metadataId, "CopyrightHolders", conn);
        removeMetadataFromTable(metadataId, "Thumbnails", conn);
        removeTaxonPaths(metadataId, conn);
        removeCopyrights(metadataId, conn);
        return true;
    }

    /**
     * Returns a ThumbnailBean associated with the
     * given metadataId.
     * If no keywords are found or an error occurs,
     * null is returned.
     */
    public ThumbnailBean getThumbnail(String metadataId, Connection conn)
            throws SQLException {
        ThumbnailBean result = null;
        if(metadataId != null) {
            Vector rows = getMetadataProperties(metadataId,
                    "*",
                    "Thumbnails",
                    conn);
            if(rows != null && rows.size() > 0) {
                try {
                    //just take the first row, there shouldn't be
                    //multiple matches...
                    Row row = (Row)rows.firstElement();
                    result = new ThumbnailBean();
                    result.setThumbnailId(row.getString("ThumbnailID"));
                    result.setMetadataId(metadataId);
                    result.setLocation(row.getString("Location"));
                    result.setFileWidth(row.getString("FileWidth"));
                    result.setFileHeight(row.getString("FileHeight"));
                } catch(NoSuchColumnException ex) {
                    throw new SQLException(ex.toString());
                }
            }
        }
        return result;
    }
    /**
     * Returns a SourceCollection Bean associated with the
     * given source collection name.
     * If no keywords are found or an error occurs,
     * null is returned.
     */
    public SourceCollectionBean getSCollectionBean(String name, Connection conn)
            throws SQLException
    {
      SourceCollectionBean result = null;
      if(name != null)
      {
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM SourceCollection WHERE name = ?");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(name));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }
        if(rows != null && rows.size() > 0) {
          try {
            //just take the first row, there shouldn't be
            //multiple matches...
            Row row = (Row)rows.firstElement();
            result = new SourceCollectionBean();
            result.setSourceId(row.getString("SourceCollectionID"));
            result.setSourceName("Name");
            result.setLocation(row.getString("Location"));
            result.setFileWidth(row.getString("Width"));
            result.setFileHeight(row.getString("Height"));
            result.setLink(row.getString("Link"));
          } catch(NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
          }
        }
      }
      return result;
    }
    /**
     * Inserts the information about the specified thumbnail,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveThumbnail(ThumbnailBean thumbnail, Connection conn)
            throws SQLException {

        if(thumbnail == null) {
            return;
        }

        StringBuffer sql = new StringBuffer();
        String thumbnailId = thumbnail.getThumbnailId();

        Vector values = new Vector();
        if(thumbnailId == null) {
            // Use INSERT statement
            sql.append(THUMBNAILINSERTSQL);
        } else {
            sql.append(THUMBNAILUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        values.addElement(new StringValue(thumbnail.getMetadataId()));
        values.addElement(new StringValue(thumbnail.getLocation()));
        values.addElement(new StringValue(thumbnail.getFileWidth()));
        values.addElement(new StringValue(thumbnail.getFileHeight()));

        if(thumbnailId != null) {
            values.addElement(new StringValue(thumbnailId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Inserts the information about the specified diseaseDiagnosis,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveDiseaseDiagnosis(DiseaseDiagnosisBean diseaseDiagnosis, Connection conn) throws SQLException {
        if(diseaseDiagnosis == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String diseaseDiagnosisId = diseaseDiagnosis.getDiseaseDiagnosisId();
        if(diseaseDiagnosisId == null) {
            // Use INSERT statement
            sql.append(DISEASEDIAGNOSISINSERTSQL);
        } else {
            sql.append(DISEASEDIAGNOSISUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(diseaseDiagnosis.getMetadataId()));
        values.addElement(new StringValue(diseaseDiagnosis.getDiseaseDiagnosis()));
        if(diseaseDiagnosisId != null) {
            values.addElement(new StringValue(diseaseDiagnosisId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Inserts the information about the specified copyright,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveCopyright(CopyrightBean copyright, Connection conn) throws SQLException {
        if(copyright == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String copyrightId = copyright.getCopyrightId();
        if(copyrightId == null) {
            // Use INSERT statement
            sql.append(COPYRIGHTINSERTSQL);
        } else {
            sql.append(COPYRIGHTUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(copyright.getMetadataId()));
        String copyrightTextId = saveCopyrightText(copyright.getCopyrightText(), conn);
        values.addElement(new StringValue(copyrightTextId));
        if(copyrightId != null) {
            values.addElement(new StringValue(copyright.getCopyrightId()));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Stores a CopyrightTextBean in the database.  If the provided
     * CopyrightTextBean has a copyrightTextId that is non-null, an update
     * will be attempted.  Otherwise, an insert will be performed.
     * The copyrightTextId will be returned.  This will either be obtained
     * from the provided bean, or by doing another database lookup after
     * the insert (looking for a match on the copyrightText).
     */
    public String saveCopyrightText(CopyrightTextBean copyrightText, Connection conn) throws SQLException {
        if(copyrightText == null) {
            return null;
        }
        String copyrightTextId = null;
        copyrightTextId = copyrightText.getCopyrightTextId();
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        Vector values = new Vector();
        StringValue textValue =
                new StringValue(copyrightText.getCopyrightText());
        values.addElement(textValue);
        values.addElement(new StringValue(copyrightText.getCost()));
        values.addElement(new StringValue(copyrightText.getCopyrightAndOtherRestriction()));
        if(copyrightTextId != null) {
            sqlCommandBean.setSqlValue(COPYRIGHTTEXTUPDATESQL);
            values.addElement(new StringValue(copyrightText.getCopyrightTextId()));
        } else {
            sqlCommandBean.setSqlValue(COPYRIGHTTEXTINSERTSQL);
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
        if(copyrightTextId == null) {
            try {
                sqlCommandBean.setSqlValue(COPYRIGHTTEXTLOOKUPIDSQL);
                values.clear();
                values.addElement(textValue);
                Vector rows;
                rows = sqlCommandBean.executeQuery();
                Row aRow = (Row)rows.firstElement();
                copyrightTextId = aRow.getString("CopyrightTextID");
            } catch(UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch(NoSuchColumnException ex) {
                throw new SQLException(ex.toString());
            }
        }
        return copyrightTextId;
    }

    /**
     * Inserts the information about the specified copyrightHolder,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveCopyrightHolder(CopyrightHolderBean copyrightHolder,
                                    Connection conn)
            throws SQLException {

        if(copyrightHolder == null) {
            return;
        }

        String vCardID = saveVCard(copyrightHolder.getVCard(), conn);

        StringBuffer sql = new StringBuffer();
        String copyrightHolderId = copyrightHolder.getCopyrightHolderId();
        if(copyrightHolderId == null) {
            // Use INSERT statement
            sql.append(COPYRIGHTHOLDERINSERTSQL);
        } else {
            sql.append(COPYRIGHTHOLDERUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(copyrightHolder.getMetadataId()));
        values.addElement(new StringValue(vCardID));

        if(copyrightHolderId != null) {
            values.addElement(new StringValue(copyrightHolderId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * @param vCard non-null vcard String
     * @param conn
     *
     * @return
     *
     * @throws SQLException
     */
    public String saveVCard(String vCard, Connection conn)
            throws SQLException {
        vCard = vCard.trim();
        String vCardID = getVCardIDByVCard(vCard, conn);

        if(vCardID == null) {
            // No current vCard found, so we have to insert one
            SQLCommandBean sqlInsertCommand = new SQLCommandBean();
            sqlInsertCommand.setConnection(conn);
            sqlInsertCommand.setSqlValue("INSERT INTO vCards (vCard) VALUES (?)");
            Vector insertValues = new Vector();
            insertValues.add(new StringValue(vCard));
            sqlInsertCommand.setValues(insertValues);
            sqlInsertCommand.executeUpdate();
            vCardID = getVCardIDByVCard(vCard, conn);
        }
        return vCardID;
    }

    /**
     * Returns a vCardID or null if no VCard matching the vCard parameter
     * is found.
     *
     * @param vCard The String of the vCard to be found
     * @param conn The connection to the database.
     *
     * @return <code>null</code>If no vCard is found, otherwise returns a
     *         <code>String</code> containing the vCardID
     */
    private String getVCardIDByVCard(String vCard, Connection conn) throws SQLException {
        SQLCommandBean sqlCommand = new SQLCommandBean();
        sqlCommand.setConnection(conn);
        sqlCommand.setSqlValue("SELECT vCardID FROM vCards WHERE vCard LIKE ?");
        Vector values = new Vector();
        values.add(new StringValue(vCard));
        sqlCommand.setValues(values);

        Vector results;
        try {
            results = sqlCommand.executeQuery();
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.getMessage());
        }
        if(results != null && results.size() > 0) {
            Row row = (Row)results.get(0);
            try {
                return row.getString("vCardID");
            } catch(NoSuchColumnException e) {
                throw new SQLException(e.getMessage());
            }
        }
        else
        return null;
    }

    public void saveTaxonPath(TaxonPathBean taxonPath) throws SQLException {
        if(null == taxonPath) {
            return;
        }
        Connection conn = dataSource.getConnection();
        try {
            saveTaxonPath(taxonPath, conn);
        } finally {
            conn.close();
        }
    }

    /**
     * Inserts the information about the specified taxon path,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveTaxonPath(TaxonPathBean taxonPath, Connection conn) throws SQLException {
        if(taxonPath == null) {
            return;
        }
        String taxonPathId = taxonPath.getTaxonPathId();
        final String source = taxonPath.getSource();
        final String purpose = taxonPath.getPurpose();
        final String description = taxonPath.getDescription();
        final String keyword = taxonPath.getKeyword();
        final String metadataId = taxonPath.getMetadataId();

        // Checks if a taxon path like this already exists in the db
        if(null == taxonPathId) {
            taxonPathId = getTaxonPathId(metadataId, source, conn);
        }

        if(taxonPathId == null) {
            // Use INSERT statement
            final String sql = TAXONPATHINSERTSQL;
            final SQLCommandBean sqlCommandBean = new SQLCommandBean();

            sqlCommandBean.setConnection(conn);
            sqlCommandBean.setSqlValue(sql);

            // Sets a temporary source so that we can grab the taxon path id with a unique value
            final String tempSource = metadataId + System.currentTimeMillis();

            Vector values = new Vector();
            values.addElement(new StringValue(metadataId));
            values.addElement(new StringValue(tempSource));
            values.addElement(new StringValue(purpose));
            values.addElement(new StringValue(description));
            values.addElement(new StringValue(keyword));
            sqlCommandBean.setValues(values);
            sqlCommandBean.executeUpdate();

            // Gets the taxon path id using the unique temporary source value
            sqlCommandBean.setSqlValue("SELECT TaxonPathID FROM " + "TaxonPaths WHERE Source = ?");
            values.clear();
            values.addElement(new StringValue(tempSource));
          //  values.addElement(new StringValue(purpose));
           // values.addElement(new StringValue(description));
          //  values.addElement(new StringValue(keyword));
            sqlCommandBean.setValues(values);

            try {
                Vector rows = sqlCommandBean.executeQuery();
                if(rows != null && rows.size() > 0) {
                    Row aRow = (Row)rows.firstElement();

                    // taxonPathId is used below to set the taxon path bean's taxon path id
                    taxonPathId = aRow.getString("TaxonPathID");

                    // fixes the temporary source
                    sqlCommandBean.setSqlValue("UPDATE TaxonPaths SET " + "Source = ?, Purpose = ?, Description = ?, Keyword = ? " + "WHERE TaxonPathID = ?");
                    values.clear();
                    values.addElement(new StringValue(source));
                    values.addElement(new StringValue(purpose));
                    values.addElement(new StringValue(description));
                    values.addElement(new StringValue(keyword));
                    values.addElement(new StringValue(taxonPathId));
                    sqlCommandBean.setValues(values);
                    sqlCommandBean.executeUpdate();
                }
            } catch(NoSuchColumnException ex) {
                throw new SQLException(ex.toString());
            } catch(UnsupportedTypeException ex2) {
                throw new SQLException(ex2.toString());
            }
        }
        taxonPath.setTaxonPathId(taxonPathId);

        // There should be a taxon path id now
        if(taxonPathId != null && taxonPath.getTaxons() != null) {
            Iterator taxonIterator = taxonPath.getTaxons().iterator();
            while(taxonIterator.hasNext()) {
                TaxonBean taxon = (TaxonBean)taxonIterator.next();
                taxon.setTaxonPathId(taxonPathId);
                saveTaxon(taxon, conn);
            }
        } else {
            throw new RuntimeException("Unable to save TaxonPath");
        }
    }

    private String getTaxonPathId(final String metadataId, final String source, final Connection conn) throws SQLException {
        final String sql = "SELECT TaxonPathId FROM TaxonPaths WHERE MetadataID = ? AND Source = ?";
        String ret = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, metadataId);
            ps.setString(2, source);
            rs = ps.executeQuery();
            if(rs.next()) {
                ret = rs.getString(1);
            }
        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }
        return ret;
    }

    /**
     * Inserts the information about the specified taxon,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveTaxon(TaxonBean taxon, Connection conn) throws SQLException {
        StringBuffer sql = new StringBuffer();
        String taxonId = taxon.getTaxonId();
        String taxonPathId = taxon.getTaxonPathId();
        String id = taxon.getId();
        String entry = taxon.getEntry();

        // Checks to see if a Taxon matching this  bean already exists
        // in the database
        String existingTaxon = getTaxonId(taxon, conn);
        if(null != existingTaxon) {
            taxon.setTaxonId(existingTaxon);
            return;
        }

        if(taxonId == null) {
            // Use INSERT statement
            sql.append(TAXONINSERTSQL);
        } else {
            sql.append(TAXONUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(taxonPathId));
        values.addElement(new StringValue(id));
        values.addElement(new StringValue(entry));
        if(taxonId != null) {
            values.addElement(new StringValue(taxonId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    private String getTaxonId(TaxonBean taxon, Connection conn) throws SQLException {
        String ret = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT TaxonID FROM Taxons WHERE Id = ? AND Entry = ? AND TaxonPathID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, taxon.getId());
            ps.setString(2, taxon.getEntry());
            ps.setString(3, taxon.getTaxonPathId());
            rs = ps.executeQuery();
            if(rs.next()) {
                ret = rs.getString(1);
            }
        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }

        return ret;
    }

    /**
     * Inserts the information about the specified contextURL,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveContextURL(ContextURLBean contextURL, Connection conn) throws SQLException {
        if(contextURL == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String contextURLId = contextURL.getContextURLId();
        if(contextURLId == null) {
            // Use INSERT statement
            sql.append(CONTEXTURLINSERTSQL);
        } else {
            sql.append(CONTEXTURLUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(contextURL.getMetadataId()));
        values.addElement(new StringValue(contextURL.getContextURL()));
        values.addElement(new StringValue(contextURL.getContextURLDescription()));
        if(contextURLId != null) {
            values.addElement(new StringValue(contextURLId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Inserts the information about the specified contributor,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveContributor(ContributorBean contributor, Connection conn)
            throws SQLException {

        if(contributor == null) {
            return;
        }

        String vCardID = saveVCard(contributor.getVCard(), conn);

        StringBuffer sql = new StringBuffer();
        String contributorId = contributor.getContributorId();
        if(contributorId == null) {
            // Use INSERT statement
            sql.append(CONTRIBUTORINSERTSQL);
        } else {
            sql.append(CONTRIBUTORUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(contributor.getMetadataId()));
        values.addElement(new StringValue(contributor.getRole()));
        values.addElement(new StringValue(vCardID));

        java.sql.Date contributeDate = null;
        if(null != contributor.getDate()) {
            contributeDate = new java.sql.Date(contributor.getDate().getTime());
        }
        values.addElement(new DateValue(contributeDate));
        values.addElement(new StringValue(contributor.getDateDescription()));
        values.addElement(new StringValue(contributor.getVersion()));
        values.addElement(new StringValue(contributor.getStatus()));
        if(contributorId != null) {
            values.addElement(new StringValue(contributor.getContributorId()));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }


    /**
     * Inserts the information about the specified requirement,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveRequirement(RequirementBean requirement, Connection conn) throws SQLException {
        if(requirement == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String requirementId = requirement.getRequirementId();
        if(requirementId == null) {
            // Use INSERT statement
            sql.append(REQUIREMENTINSERTSQL);
        } else {
            sql.append(REQUIREMENTUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(requirement.getMetadataId()));
        values.addElement(new StringValue(requirement.getRequirementType()));
        values.addElement(new StringValue(requirement.getRequirementName()));
        values.addElement(new StringValue(requirement.getOtherPlatform()));
        values.addElement(new StringValue(requirement.getDuration()));
        values.addElement(new StringValue(requirement.getDescription()));
        if(requirementId != null) {
            values.addElement(new StringValue(requirement.getRequirementId()));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Inserts the information about the specified relation,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveRelation(RelationBean relation, Connection conn) throws SQLException {
        if(relation == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String relationId = relation.getRelationId();
        if(relationId == null) {
            // Use INSERT statement
            sql.append(RELATIONINSERTSQL);
        } else {
            sql.append(RELATIONUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(relation.getMetadataId()));
        values.addElement(new StringValue(relation.getResource()));
        values.addElement(new StringValue(relation.getKind()));
        values.addElement(new StringValue(relation.getDescription()));
        values.addElement(new StringValue(relation.getCatalogue()));
        values.addElement(new StringValue(relation.getEntry()));
        if(relationId != null) {
            values.addElement(new StringValue(relation.getRelationId()));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Inserts the information about the specified keyword,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveKeyword(KeywordBean keyword, Connection conn) throws SQLException {
        if(keyword == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String keywordId = keyword.getKeywordId();
        if(keywordId == null) {
            // Use INSERT statement
            sql.append(KEYWORDINSERTSQL);
        } else {
            sql.append(KEYWORDUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(keyword.getMetadataId()));
        values.addElement(new StringValue(keyword.getKeyword()));
        if(keywordId != null) {
            values.addElement(new StringValue(keywordId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Inserts the information about the specified keyword,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveTargetUserGroup(TargetUserGroupBean tug, Connection conn) throws SQLException {
        if(tug == null) {
            return;
        }
        List<TargetUserGroupBean> existingTugs = new ArrayList<TargetUserGroupBean>();
        Vector rows = null;
        if(null != tug.getMetadataId()) {
            rows = getMetadataProperties(tug.getMetadataId(), "TargetUserGroup", "TargetUserGroups", conn);
        }
        if(null != rows) {
            try {
                for(Iterator iter = rows.iterator(); iter.hasNext(); ) {
                    Row row = (Row)iter.next();
                    String tugDescription = row.getString("TargetUserGroup");
                    TargetUserGroupBean temp = new TargetUserGroupBean();
                    temp.setMetadataId(tug.getMetadataId());
                    temp.setTargetUserGroup(tugDescription);
                    existingTugs.add(temp);
                }
            } catch(NoSuchColumnException e) {
                throw new RuntimeException(e);
            }
        }
        if(null != tug.getTargetUserGroup()) {
            for(TargetUserGroupBean existingTug : existingTugs) {
                if(tug.getTargetUserGroup().equals(existingTug.getTargetUserGroup())) {
                    // We don't need to save this TUG, because it already exists
                    return;
                }
            }
        }
        String sql = TARGETUSERGROUPINSERTSQL;
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(tug.getMetadataId()));
        values.addElement(new StringValue(tug.getTargetUserGroup()));
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    public void deleteTargetUserGroupsByMetadataId(final String metadataId, final Connection conn) throws SQLException {
        if(null == metadataId) return;

        final String sql = "DELETE FROM TargetUserGroups WHERE MetadataId = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, metadataId);
            ps.executeUpdate();
        } finally {
            if(null != ps) {
                ps.close();
            }
        }
    }

    /**
     * Inserts the information about the specified format,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveFormat(FormatBean format, Connection conn) throws SQLException {
        if(format == null) {
            return;
        }
        StringBuffer sql = new StringBuffer();
        String formatId = format.getFormatId();
        if(formatId == null) {
            // Use INSERT statement
            sql.append(FORMATINSERTSQL);
        } else {
            sql.append(FORMATUPDATESQL);
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(format.getMetadataId()));
        values.addElement(new StringValue(format.getFormat()));
        if(formatId != null) {
            values.addElement(new StringValue(formatId));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    public List getMetametadataIdentifiers(String metadataId, Connection conn) {
        List<MetametadataIdentifierBean> ret = new ArrayList<MetametadataIdentifierBean>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT MetametadataIdentifierID, Catalogue, Entry, MetadataSchema FROM MetametadataIdentifiers WHERE MetadataID = ?");
            ps.setString(1, metadataId);
            rs = ps.executeQuery();
            while(rs.next()) {
                MetametadataIdentifierBean mib = new MetametadataIdentifierBean();
                mib.setMetametadataIdentifierId(rs.getString(1));
                mib.setMetadataId(metadataId);
                mib.setCatalog(rs.getString(2));
                mib.setEntry(rs.getString(3));
                mib.setMetadataSchema(rs.getString(4));
                ret.add(mib);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(null != rs) {
                    rs.close();
                }
                if(null != ps) {
                    ps.close();
                }
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ret;
    }

    public List getMetametadataContributors(String metadataId, Connection conn) throws SQLException {
        List<MetametadataContributorBean> ret = new ArrayList<MetametadataContributorBean>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT A.MetametadataContributorID, A.Role, A.ContributeDate, A.ContributeDateDescription, B.vCard FROM MetametadataContributors A INNER JOIN vCards B ON A.vCardID = B.vCardID WHERE A.MetadataID = ?");
            ps.setString(1, metadataId);
            rs = ps.executeQuery();
            while(rs.next()) {
                MetametadataContributorBean mcb = new MetametadataContributorBean();
                mcb.setMetametadataContributorId(rs.getString(1));
                mcb.setRole(rs.getString(2));
                mcb.setDate(rs.getDate(3));
                mcb.setDateDescription(rs.getString(4));
                mcb.setvCard(rs.getString(5));
                mcb.setMetadataId(metadataId);

                ret.add(mcb);
            }
        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }

        return ret;
    }
}