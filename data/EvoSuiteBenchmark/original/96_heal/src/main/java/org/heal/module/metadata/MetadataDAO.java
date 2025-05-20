package org.heal.module.metadata;

import com.ora.jsp.sql.NoSuchColumnException;
import com.ora.jsp.sql.Row;
import com.ora.jsp.sql.SQLCommandBean;
import com.ora.jsp.sql.UnsupportedConversionException;
import com.ora.jsp.sql.UnsupportedTypeException;
import com.ora.jsp.sql.value.BooleanValue;
import com.ora.jsp.sql.value.StringValue;
import com.ora.jsp.sql.value.TimestampValue;

import org.heal.util.CommonDAO;
import org.heal.util.FileLocator;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.List;

/**
 * This DAO was created by Grace, its content came from original MetadataServicesBean.java.
 * This contains the metadata access database functionality of
 * saving metadata as well as looking up short and IMS
 * records.  It also supports generating a metadata
 * collection in XML (for NSDL support) as well as
 * generating a list of content IDs for the NSDL.
 *
 * @author Seth Wright
 * @author Grace Yang
 */

public class MetadataDAO implements Serializable {
    public MetadataDAO() {
    }

    private DataSource dataSource;
    private CommonDAO cd = new CommonDAO();
    private FileLocator fileLocator = null;

    private static final String METADATAINSERTSQL = "INSERT INTO Metadata (FileName, FileSize, Title, Location, SourceCollection, SourceCollectionID, ContributeUserID, ContributeDate, Annotated, Inappropriate, Archived, Private, Description, PublicationId, SubmissionAgreement, LearningResourceType, SpecimenType, RadiographType, Orientation, Magnification, ClinicalHistory, FileWidth, FileHeight, Duration, ApproveDate, CatalogDate, RejectDate, CreationDate, LanguageType) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String METADATAUPDATESQL = "UPDATE Metadata SET FileName = ?, FileSize = ?, Title = ?, Location = ?, SourceCollection = ?, SourceCollectionID = ?, ContributeUserID = ?, ContributeDate = ?, Annotated = ?, Inappropriate = ?, Archived = ?, Private = ?, Description = ?, PublicationId = ?, SubmissionAgreement = ?, LearningResourceType = ?, SpecimenType = ?, RadiographType = ?, Orientation = ?, Magnification = ?, ClinicalHistory = ?, FileWidth = ?, FileHeight = ?, Duration = ?, ApproveDate = ?, CatalogDate = ?, RejectDate = ?, CreationDate = ? WHERE MetadataID = ?";

    private static final String PUBLICATIONINSERTSQL = "INSERT INTO Publications (Name, PublicationDate) VALUES (?, ?)";
    private static final String PUBLICATIONUPDATESQL = "UPDATE Publications SET Name = ?, PublicationDate = ? WHERE PublicationId = ?";

    private static final String METAMETADATA_IDENTIFIER_INSERT_SQL = "INSERT INTO MetametadataIdentifiers (MetadataID, Catalogue, Entry, MetadataSchema) VALUES (?, ?, ?, ?)";
    private static final String METAMETADATA_IDENTIFIER_UPDATE_SQL = "UPDATE MetametadataIdentifiers SET MetadataID = ?, Catalogue = ?, Entry = ?, MetadataSchema = ? WHERE MetametadataIdentifierID = ?";

    private static final String METAMETADATA_CONTRIBUTOR_INSERT_SQL = "INSERT INTO MetametadataContributors (MetadataID, Role, ContributeDate, ContributeDateDescription, vCardID) VALUES (?, ?, ?, ?, ?)";
    private static final String METAMETADATA_CONTRIBUTOR_UPDATE_SQL = "UPDATE MetametadataContributors SET MetadataID = ?, Role = ?, ContributeDate = ?, ContributeDateDescription = ?, vCardID = ? WHERE MetametadataContributorID = ?";

    /**
     * Sets the dataSource property value.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setFileLocator(FileLocator newFileLocator) {
        fileLocator = newFileLocator;
    }

    /**
     * Returns a MetadataBean initialized with the information
     * found in the database specified by the given metadataId,
     * or null if the metadataId is not found in the database.
     */
    public MetadataBean getMetadata(String metadataId)
            throws SQLException {
        Connection conn = dataSource.getConnection();
        MetadataBean metaBean = null;
        try {
            metaBean = cd.getMetadata(metadataId, conn);
        } finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                //ignore...
            }
        }
        return metaBean;
    }

    /**
     * Inserts the information about the specified metadata, or
     * updates the information if it is already defined in the
     * database.
     */
    public void saveMetadata(MetadataBean metadataInfo) throws SQLException {
        // Save the metadata info from the database
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            saveMetadata(metadataInfo, conn);
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
     * Inserts the information about the specified metadata,
     * or updates the information if it's already defined in the
     * database.
     */
    public void saveMetadata(MetadataBean metadata, Connection conn) throws SQLException {
        if(metadata == null) {
            return;
        }

        String sql;
        MetadataBean dbInfo = cd.getMetadata(metadata.getMetadataId(), conn);
        if(dbInfo == null) {
            // Use INSERT statement
            sql = METADATAINSERTSQL;
        } else {
            sql = METADATAUPDATESQL;
        }

        // Inserts the publication first in case we need the id
        if(null != metadata.getPublicationName() || null != metadata.getPublicationId()
                || null != metadata.getPublicationDate()) {
            final int publicationId = savePublication(metadata.getPublicationId(), metadata.getPublicationName(), metadata.getPublicationDate());
            metadata.setPublicationId(String.valueOf(publicationId));
        }

        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        sqlCommandBean.setSqlValue(sql);
        Vector values = new Vector();
        values.addElement(new StringValue(metadata.getFileName()));
        values.addElement(cd.getLongValue(metadata.getFileSize()));
        values.addElement(new StringValue(metadata.getTitle()));
        values.addElement(new StringValue(metadata.getLocation()));
        values.addElement(new StringValue(metadata.getSourceCollection()));
        values.addElement(new StringValue(metadata.getSourceCollectionId()));
        values.addElement(cd.getIntValue(metadata.getContributeUserId()));
        values.addElement(cd.getTimestampValue(metadata.getContributeDate()));
        values.addElement(new BooleanValue(metadata.isAnnotated()));
        values.addElement(new BooleanValue(metadata.isInappropriate()));
        values.addElement(new BooleanValue(metadata.isArchived()));
        values.addElement(new BooleanValue(metadata.isPrivate()));
        values.addElement(new StringValue(metadata.getDescription()));
        values.addElement(new StringValue(metadata.getPublicationId()));
        values.addElement(new StringValue(metadata.getSubmissionAgreement()));
        values.addElement(new StringValue(metadata.getLearningResourceType()));
        values.addElement(new StringValue(metadata.getSpecimenType()));
        values.addElement(new StringValue(metadata.getRadiographType()));
        values.addElement(new StringValue(metadata.getOrientation()));
        values.addElement(new StringValue(metadata.getMagnification()));
        values.addElement(new StringValue(metadata.getClinicalHistory()));
        values.addElement(cd.getIntValue(metadata.getFileWidth()));
        values.addElement(cd.getIntValue(metadata.getFileHeight()));
        values.addElement(cd.getIntValue(metadata.getDuration()));
        values.addElement(cd.getTimestampValue(metadata.getApproveDate()));
        values.addElement(cd.getTimestampValue(metadata.getCatalogDate()));
        values.addElement(cd.getTimestampValue(metadata.getRejectDate()));
        values.addElement(cd.getTimestampValue(metadata.getCreationDate()));
        values.addElement(new StringValue(metadata.getLanguageType()));
        if(dbInfo != null) {
            values.addElement(new StringValue(metadata.getMetadataId()));
        }
        sqlCommandBean.setValues(values);
        sqlCommandBean.executeUpdate();
    }

    /**
     * Returns the short record for the specified metadata in XML format.
     */
    public String getShortRecord(String metadataId)
            throws SQLException, IOException {
        ShortMetadataBean metaInfo = getShortMetadata(metadataId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HealMetadataXMLConverter.shortMetadataToXML(metaInfo, stream);
        return stream.toString();
    }

    public ShortMetadataBean getShortMetadata(String metadataId)
            throws SQLException {
        Connection conn = dataSource.getConnection();
        // Get the metadata info from the database
        Row metadataRow = cd.getMetadataProperty(metadataId,
                "MetadataID, GlobalID, " +
                "FileName, " +
                "FileSize, Title, " +
                "Description, Location, " +
                "SourceCollection, " +
                "FileWidth, FileHeight, " +
                "ContributeUserID," +
                "LearningResourceType",
                "Metadata",
                conn);

        if(metadataRow == null) {
            return null;
        }

        ShortMetadataBean metadataInfo = new ShortMetadataBean();
        try {
            String sc = (String)metadataRow.getString("SourceCollection");
            metadataInfo.setMetadataId(metadataRow.getString("MetadataID"));
            metadataInfo.setGlobalId(metadataRow.getString("GlobalID"));
            metadataInfo.setFileName(metadataRow.getString("FileName"));
            metadataInfo.setFileSize(metadataRow.getString("FileSize"));
            metadataInfo.setTitle(metadataRow.getString("Title"));
            metadataInfo.setDescription(metadataRow.getString("Description"));
            metadataInfo.setLocation(metadataRow.getString("Location"));
            metadataInfo.setSourceCollection(metadataRow.getString("SourceCollection"));
            metadataInfo.setFileWidth(metadataRow.getString("FileWidth"));
            metadataInfo.setFileHeight(metadataRow.getString("FileHeight"));
            metadataInfo.setContributeUserId(metadataRow.getString("ContributeUserID"));
            metadataInfo.setLearningResourceType(metadataRow.getString("LearningResourceType"));
            metadataInfo.setCollectionBean(cd.getSCollectionBean(sc, conn));
            metadataInfo.setThumbnail(cd.getThumbnail(metadataId, conn));
            String format = cd.getMetadataPropertyAsString(metadataId, "Format", "Formats", conn);
            metadataInfo.setFormat(format);
            /*        } catch (UnsupportedConversionException uce) {
                  throw new SQLException(uce.toString());
            */
        } catch(NoSuchColumnException nsce) {
            throw new SQLException(nsce.toString());
        } finally {
            try {
                conn.close();
            } catch(SQLException ex) {
                //ignore...
            }
        }

        return metadataInfo;
    }

    /**
     * Returns the IMS record for the specified metadata in XML format.
     * XXX Is the parameter here the global id or the metadata id?
     */
    public String getIMSRecord(String metadataId) throws SQLException, IOException {
        CompleteMetadataBean metaInfo = getCompleteMetadata(metadataId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HealMetadataXMLConverter.metadataToIMSXML(metaInfo, fileLocator, stream);
        return stream.toString();
    }

    /**
     * Returns information about the HEAL system as a whole.  This is to be
     * returned to the NSDL, but the spec isn't out yet, so we don't know
     * what to include here.
     */
    public String getCollectionMetadata() {
        throw new UnsupportedOperationException("getCollectionMetadata not implemented");
    }


    public CompleteMetadataBean getCompleteMetadata(String metadataId) throws SQLException {
        Connection conn = dataSource.getConnection();
        CompleteMetadataBean result;
        try {
            result = getCompleteMetadata(metadataId, conn);
        } finally {
            try {
                conn.close();
            } catch(SQLException ex2) {
                //ignore
            }
        }
        return result;
    }

    public CompleteMetadataBean getCompleteMetadata(String metadataId, Connection conn) throws SQLException {
        CompleteMetadataBean result = new CompleteMetadataBean();
        try {
            result.setMetadataId(metadataId);
            MetadataBean m = cd.getMetadata(metadataId, conn);
            String name= m.getSourceCollection();
            result.setMetadata(m);
            result.setDiseaseDiagnoses(getDiseaseDiagnoses(metadataId, conn));
            result.setCopyrights(getCopyrights(metadataId, conn));
            result.setTaxonPaths(getTaxonPaths(metadataId, conn));
            result.setCopyrightHolders(getCopyrightHolders(metadataId, conn));
            result.setContextURLs(getContextURLs(metadataId, conn));
            result.setRequirements(getRequirements(metadataId, conn));
            result.setContributors(getContributors(metadataId, conn));
            result.setRelations(getRelations(metadataId, conn));
            result.setKeywords(getKeywords(metadataId, conn));
            result.setTargetUserGroups(getTargetUserGroups(metadataId, conn));
            result.setFormats(getFormats(metadataId, conn));
            result.setThumbnail(cd.getThumbnail(metadataId, conn));
            result.setCollectionBean(cd.getSCollectionBean(name, conn));
            result.setMetametadataIdentifiers(cd.getMetametadataIdentifiers(metadataId, conn));
            result.setMetametadataContributors(cd.getMetametadataContributors(metadataId, conn));
        } catch(NoSuchColumnException ex) {
            throw new SQLException(ex.toString());
        }
        return result;
    }

    /**
     * @param metadataId The metadataId of the target user groups to look up.
     * @param conn The connection to the database to query.
     *
     * @return Am ArrayList of {@link org.heal.module.metadata.TargetUserGroupBean TargetUserGroupBeans}.
     *         This method will never return <code>null</code>, but may return an empty
     *         list if no target user groups are found for the given metadataId.
     *
     * @throws SQLException Thrown when a database error occurs.
     */
    private ArrayList getTargetUserGroups(String metadataId, Connection conn)
            throws SQLException {
        ArrayList ret = new ArrayList();
        Vector rows = cd.getMetadataProperties(metadataId, "*", "TargetUserGroups", conn);
        if(null != rows) {
            for(Iterator iter = rows.iterator(); iter.hasNext();) {
                Row row = (Row)iter.next();

                try {
                    TargetUserGroupBean tug = new TargetUserGroupBean();
                    tug.setMetadataId(metadataId);
                    tug.setTargetUserGroup(row.getString("TargetUserGroup"));
                    ret.add(tug);
                } catch(NoSuchColumnException e) {
                    throw new RuntimeException(e);
                    // TODO logging?
                }
            }
        }
        return ret;
    }


    /**
     * Saves a CompleteMetadataBean.  Only non-null properties are stored in
     * the database.  For all properties except the MetadataBean, if the
     * properties ID (i.e. TaxonID for a TaxonBean) is not null, an update
     * will be performed for that entry.  If the ID is null, then an insert
     * will be performed.
     * If a set of data (for example the keywords) is null or empty, then no
     * changes are made to those entries in the database.  If it is not
     * null, then the keywords are added to the database, but the previous
     * keywords stored in the database are not removed.  If you want to have
     * the previous settings for the complete metadata bean fields removed,
     * use updateCompleteMetadata.
     */
    public void saveCompleteMetadata(CompleteMetadataBean cmb) throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            saveCompleteMetadata(cmb, conn);
            conn.commit();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch(SQLException ex2) {
                //ignore
            }
        }
    }

    /**
     * If the {@link CompleteMetadataBean} does not have a metadataId (for
     * example if it represents a new record not yet in the database), then
     * the <code>Connection</code> <em>must</em> be commited during the
     * execution of this method.  However if a metadataId <em>does</em> exist,
     * then the transactional state of the <code>Connection</code> will not
     * be modified here.
     *
     * @see #saveCompleteMetadata(CompleteMetadataBean)
     */
    public void saveCompleteMetadata(CompleteMetadataBean cmb, Connection conn) throws SQLException {
        List elems;
        Iterator elemIterator;

        saveMetadata(cmb, conn);

        String metadataId = cmb.getMetadataId();
        if(metadataId == null) {
            conn.commit(); //we need to store the metadata
            //now we look it up via the location so that we can get the id.
            metadataId = getMetadataIdFromProperty(cmb.getLocation(),
                    "Location",
                    conn);
        }

        cmb.setMetadataId(metadataId);
        elems = cmb.getDiseaseDiagnoses();
        if(elems != null) {
            elemIterator = elems.iterator();
            DiseaseDiagnosisBean elem;
            while(elemIterator.hasNext()) {
                elem = (DiseaseDiagnosisBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveDiseaseDiagnosis(elem, conn);
            }
        }

        elems = cmb.getCopyrights();
        if(elems != null) {
            elemIterator = elems.iterator();
            CopyrightBean elem;
            while(elemIterator.hasNext()) {
                elem = (CopyrightBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveCopyright(elem, conn);
            }
        }

        elems = cmb.getTaxonPaths();
        if(elems != null) {
            elemIterator = elems.iterator();
            TaxonPathBean elem;
            while(elemIterator.hasNext()) {
                elem = (TaxonPathBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveTaxonPath(elem, conn);
            }
        }
        elems = cmb.getCopyrightHolders();
        if(elems != null) {
            elemIterator = elems.iterator();
            CopyrightHolderBean elem;
            while(elemIterator.hasNext()) {
                elem = (CopyrightHolderBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveCopyrightHolder(elem, conn);
            }
        }
        elems = cmb.getContextURLs();
        if(elems != null) {
            elemIterator = elems.iterator();
            ContextURLBean elem;
            while(elemIterator.hasNext()) {
                elem = (ContextURLBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveContextURL(elem, conn);
            }
        }
        elems = cmb.getRequirements();
        if(elems != null) {
            elemIterator = elems.iterator();
            RequirementBean elem;
            while(elemIterator.hasNext()) {
                elem = (RequirementBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveRequirement(elem, conn);
            }
        }

        TreeMap contributors = cmb.getContributors();
        Iterator contributorsIterator = contributors.values().iterator();
        while(contributorsIterator.hasNext()) {
            elems = (ArrayList)contributorsIterator.next();
            if(elems != null) {
                elemIterator = elems.iterator();
                ContributorBean elem;
                while(elemIterator.hasNext()) {
                    elem = (ContributorBean)elemIterator.next();
                    elem.setMetadataId(metadataId);
                    cd.saveContributor(elem, conn);
                }
            }
        }

        elems = cmb.getRelations();
        if(elems != null) {
            elemIterator = elems.iterator();
            RelationBean elem;
            while(elemIterator.hasNext()) {
                elem = (RelationBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveRelation(elem, conn);
            }
        }
        elems = cmb.getKeywords();
        if(elems != null) {
            elemIterator = elems.iterator();
            KeywordBean elem;
            while(elemIterator.hasNext()) {
                elem = (KeywordBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveKeyword(elem, conn);
            }
        }

        elems = cmb.getTargetUserGroups();
        if(null != elems) {
            TargetUserGroupBean elem;
            elemIterator = elems.iterator();
            while(elemIterator.hasNext()) {
                elem = (TargetUserGroupBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveTargetUserGroup(elem, conn);
            }
        }

        elems = cmb.getFormats();
        if(elems != null) {
            elemIterator = elems.iterator();
            FormatBean elem;
            while(elemIterator.hasNext()) {
                elem = (FormatBean)elemIterator.next();
                elem.setMetadataId(metadataId);
                cd.saveFormat(elem, conn);
            }
        }
        ThumbnailBean thumbnail = cmb.getThumbnail();
        if(thumbnail != null) {
            thumbnail.setMetadataId(metadataId);
            cd.saveThumbnail(thumbnail, conn);
        }

        elems = cmb.getMetametadataIdentifiers();
        if(null != elems) {
            MetametadataIdentifierBean mib;
            elemIterator = elems.listIterator();
            while(elemIterator.hasNext()) {
                mib = (MetametadataIdentifierBean)elemIterator.next();
                mib.setMetadataId(metadataId);
                saveMetametadataIdentifier(mib, conn);
            }
        }

        elems = cmb.getMetametadataContributors();
        if(null != elems) {
            MetametadataContributorBean mcb;
            elemIterator = elems.listIterator();
            while(elemIterator.hasNext()) {
                mcb = (MetametadataContributorBean)elemIterator.next();
                mcb.setMetadataId(metadataId);
                saveMetametadataContributor(mcb, conn);
            }
        }
    }

    /**
     * Updates a CompleteMetadataBean.
     * This differs from the saveCompleteMetadata method in that
     * it will remove entries in the database that do not show up
     * in the complete metadata parameter.
     */
    public void updateCompleteMetadata(CompleteMetadataBean cmb)
            throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            updateCompleteMetadata(cmb, conn);
            conn.commit();
        } finally {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch(SQLException ex2) {
                //ignore
            }
        }
    }


    /**
     * Updates a CompleteMetadataBean.
     * See updateCompleteMetadata(cmb)
     */
    public void updateCompleteMetadata(CompleteMetadataBean cmb,
                                       Connection conn)
            throws SQLException {
        if(cmb == null ||
                cmb.getMetadataId() == null ||
                conn == null) {
            return;
        }
        String metadataId = cmb.getMetadataId();
        /* STRATEGY ALERT: (by Seth Wright)
         * In the interest of finishing this portion of the project on time
         * I am taking a slight shortcut.  Rather than determining the
         * difference between the current database settings and the one we
         * want, I am simply deleting all old entries and adding new ones.
         * This could result in a large growth in the id numbers of
         * such tables as keywords entries.
         * So, here is what we do, we remove all settings from the database
         * that are one to many relations and then call saveCompleteMetadata
         * The fields with multiple entries are:
         * taxons/taxonpaths
         * relations
         * requirements
         * keywords
         * formats
         * disease diagnoses
         * copyright holders
         * contexturls
         * contributors
         * copyrights/copyrighttexts
         */
        cd.removeMetadataFromTable(metadataId, "Relations", conn);
        cd.removeMetadataFromTable(metadataId, "Requirements", conn);
        cd.removeMetadataFromTable(metadataId, "Keywords", conn);
        cd.removeMetadataFromTable(metadataId, "Formats", conn);
        cd.removeMetadataFromTable(metadataId, "DiseaseDiagnoses", conn);
        cd.removeMetadataFromTable(metadataId, "CopyrightHolders", conn);
        cd.removeMetadataFromTable(metadataId, "ContextURLs", conn);
        cd.removeMetadataFromTable(metadataId, "Contributors", conn);
        cd.removeMetadataFromTable(metadataId, "TargetUserGroups", conn);
        cd.removeTaxonPaths(metadataId, conn);
        cd.removeCopyrights(metadataId, conn);
        doUpdateHack(cmb);
        saveCompleteMetadata(cmb, conn);
    }

    /**
     * The update hack is where the IDs (such as
     * KeywordId) are stripped from the complete metadata bean.
     * This is done so that saveCompleteMetadata works correctly.
     */
    private void doUpdateHack(CompleteMetadataBean cmb) {
        ArrayList elems;
        Iterator elemIterator;
        /* change log
        elems = cmb.getDiseaseDiagnoses();
        if (elems != null) {
            elemIterator = elems.iterator();
            DiseaseDiagnosisBean elem;
            while (elemIterator.hasNext()) {
            elem = (DiseaseDiagnosisBean)elemIterator.next();
            elem.setDiseaseDiagnosisId(null);
            }
        }*/

        elems = cmb.getCopyrights();
        if(elems != null) {
            elemIterator = elems.iterator();
            CopyrightBean elem;
            while(elemIterator.hasNext()) {
                elem = (CopyrightBean)elemIterator.next();
                elem.setCopyrightId(null);
            }
        }

        elems = cmb.getTaxonPaths();
        if(elems != null) {
            elemIterator = elems.iterator();
            TaxonPathBean elem;
            while(elemIterator.hasNext()) {
                elem = (TaxonPathBean)elemIterator.next();
                elem.setTaxonPathId(null);
            }
        }
        elems = cmb.getCopyrightHolders();
        if(elems != null) {
            elemIterator = elems.iterator();
            CopyrightHolderBean elem;
            while(elemIterator.hasNext()) {
                elem = (CopyrightHolderBean)elemIterator.next();
                elem.setCopyrightHolderId(null);
            }
        }
        elems = cmb.getContextURLs();
        if(elems != null) {
            elemIterator = elems.iterator();
            ContextURLBean elem;
            while(elemIterator.hasNext()) {
                elem = (ContextURLBean)elemIterator.next();
                elem.setContextURLId(null);
            }
        }
        elems = cmb.getRequirements();
        if(elems != null) {
            elemIterator = elems.iterator();
            RequirementBean elem;
            while(elemIterator.hasNext()) {
                elem = (RequirementBean)elemIterator.next();
                elem.setRequirementId(null);
            }
        }

        TreeMap contributors = cmb.getContributors();
        Iterator contributorsIterator = contributors.values().iterator();
        while(contributorsIterator.hasNext()) {
            elems = (ArrayList)contributorsIterator.next();
            if(elems != null) {
                elemIterator = elems.iterator();
                ContributorBean elem;
                while(elemIterator.hasNext()) {
                    elem = (ContributorBean)elemIterator.next();
                    elem.setContributorId(null);
                }
            }
        }

        elems = cmb.getRelations();
        if(elems != null) {
            elemIterator = elems.iterator();
            RelationBean elem;
            while(elemIterator.hasNext()) {
                elem = (RelationBean)elemIterator.next();
                elem.setRelationId(null);
            }
        }
        elems = cmb.getKeywords();
        if(elems != null) {
            elemIterator = elems.iterator();
            KeywordBean elem;
            while(elemIterator.hasNext()) {
                elem = (KeywordBean)elemIterator.next();
                elem.setKeywordId(null);
            }
        }

        elems = cmb.getFormats();
        if(elems != null) {
            elemIterator = elems.iterator();
            FormatBean elem;
            while(elemIterator.hasNext()) {
                elem = (FormatBean)elemIterator.next();
                elem.setFormatId(null);
            }
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

    /**
     * Returns a ArrayList of DiseaseDiagnosisBeans associated with the
     * given metadataId.
     * If no diagnoses are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getDiseaseDiagnoses(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "DiseaseDiagnosisID, " + "DiseaseDiagnosis", "DiseaseDiagnoses", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                DiseaseDiagnosisBean dd;
                String id;
                String ddString;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    dd = new DiseaseDiagnosisBean();
                    id = row.getString("DiseaseDiagnosisID");
                    ddString = row.getString("DiseaseDiagnosis");
                    dd.setDiseaseDiagnosisId(id);
                    dd.setMetadataId(metadataId);
                    dd.setDiseaseDiagnosis(ddString);
                    results.add(dd);
                }
            }
        }
        return results;
    }

    /**
     * Returns a ArrayList of CopyrightBeans associated with the
     * given metadataId.
     * If no copyrightss are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getCopyrights(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "CopyrightID, CopyrightTextID", "Copyrights", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                CopyrightBean copyright;
                CopyrightTextBean copyrightText;
                String copyrightTextString;
                String copyrightId;
                String copyrightTextId;
                SQLCommandBean sqlCommandBean = new SQLCommandBean();
                sqlCommandBean.setConnection(conn);
                sqlCommandBean.setSqlValue("SELECT CopyrightText FROM CopyrightTexts WHERE CopyrightTextID = ?");
                Vector values = new Vector();
                Vector copyrighttextrows = null;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    copyright = new CopyrightBean();
                    copyrightId = row.getString("CopyrightID");
                    copyrightTextId = row.getString("CopyrightTextID");
                    copyright.setCopyrightId(copyrightId);
                    copyright.setMetadataId(metadataId);
                    copyrightText = null;
                    values.clear();
                    values.addElement(new StringValue(copyrightTextId));
                    sqlCommandBean.setValues(values);
                    try {
                        copyrighttextrows = sqlCommandBean.executeQuery();
                    } catch(UnsupportedTypeException e) {
                        throw new SQLException(e.toString());
                    }
                    if(copyrighttextrows != null && copyrighttextrows.size() > 0) {
                        row = (Row)copyrighttextrows.firstElement();
                        copyrightTextString = row.getString("CopyrightText");
                        copyrightText = new CopyrightTextBean();
                        copyrightText.setCopyrightTextId(copyrightTextId);
                        copyrightText.setCopyrightText(copyrightTextString);
                    }
                    copyright.setCopyrightText(copyrightText);
                    results.add(copyright);
                }
            }
        }
        return results;
    }

    /**
     * Returns a ArrayList of CopyrightHolderBeans associated with the
     * given metadataId.
     * If no copyright holders are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getCopyrightHolders(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            // queries the database
            Vector rows;
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT a.CopyrightHolderID, b.vCard ");
            sql.append("FROM  CopyrightHolders a INNER JOIN");
            sql.append("  vCards b ON a.vCardID = b.vCardID ");
            sql.append("WHERE  (a.MetadataID = ?)");
            sqlCommandBean.setSqlValue(sql.toString());
            Vector values = new Vector();
            values.addElement(new StringValue(metadataId));
            sqlCommandBean.setValues(values);
            try {
                rows = sqlCommandBean.executeQuery();
            } catch(UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            }
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                CopyrightHolderBean ch;
                String id;
                String chString;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    ch = new CopyrightHolderBean();
                    id = row.getString("CopyrightHolderID");
                    chString = row.getString("VCard");
                    ch.setCopyrightHolderId(id);
                    ch.setMetadataId(metadataId);
                    ch.setVCard(chString);
                    results.add(ch);
                }
            }
        }
        return results;
    }


    /**
     * Returns a ArrayList of TaxonPathBeans associated with the given
     * metadataId.
     * If no taxonPathss are found or an error occurs, an empty
     * ArrayList is returned.
     */

    public ArrayList getTaxonPaths(String metadataId) throws SQLException{
        Connection conn = dataSource.getConnection();
        try{
          return getTaxonPaths(metadataId, conn);
        }
        catch(NoSuchColumnException ex) {
          throw new SQLException(ex.toString());
        }
    }


    /**
     * Returns a ArrayList of TaxonPathBeans associated with the given
     * metadataId.
     * If no taxonPathss are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getTaxonPaths(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "TaxonPathID, Source", "TaxonPaths", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                TaxonPathBean taxonPath;
                String taxonPathId;
                String taxonPathSource;
                TaxonBean taxon = null;
                String taxonId, id, entry;
                SQLCommandBean sqlCommandBean = new SQLCommandBean();
                sqlCommandBean.setConnection(conn);
                sqlCommandBean.setSqlValue("SELECT TaxonID, ID, Entry FROM Taxons WHERE TaxonPathID = ?");
                Vector values = new Vector();
                Vector taxonrows = null;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    taxonPath = new TaxonPathBean();
                    taxonPathId = row.getString("TaxonPathID");
                    taxonPathSource = row.getString("Source");
                    taxonPath.setTaxonPathId(taxonPathId);
                    taxonPath.setMetadataId(metadataId);
                    taxonPath.setSource(taxonPathSource);
                    values.clear();
                    values.addElement(new StringValue(taxonPathId));
                    sqlCommandBean.setValues(values);
                    try {
                        taxonrows = sqlCommandBean.executeQuery();
                    } catch(UnsupportedTypeException e) {
                        throw new SQLException(e.toString());
                    }
                    if(taxonrows != null && taxonrows.size() > 0) {
                        Iterator taxonIterator = taxonrows.iterator();
                        while(taxonIterator.hasNext()) {
                            row = (Row)taxonIterator.next();
                            taxon = new TaxonBean();
                            taxonId = row.getString("TaxonID");
                            id = row.getString("ID");
                            entry = row.getString("Entry");
                            taxon.setTaxonId(taxonId);
                            taxon.setTaxonPathId(taxonPathId);
                            taxon.setId(id);
                            taxon.setEntry(entry);
                            taxonPath.addTaxon(taxon);
                        }
                    }
                    results.add(taxonPath);
                }
            }
        }
        return results;
    }

    /**
     * Returns a ArrayList of ContextURLBeans associated with the given
     * If no contextURLs are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getContextURLs(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "ContextURLID, ContextURL, ContextURLDescription", "ContextURLs", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                ContextURLBean contextURL;
                String contextURLId;
                String contextURLString;
                String contextURLDescription;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    contextURL = new ContextURLBean();
                    contextURLId = row.getString("ContextURLID");
                    contextURLString = row.getString("ContextURL");
                    contextURLDescription = row.getString("ContextURLDescription");
                    contextURL.setContextURLId(contextURLId);
                    contextURL.setMetadataId(metadataId);
                    contextURL.setContextURL(contextURLString);
                    contextURL.setContextURLDescription(contextURLDescription);
                    results.add(contextURL);
                }
            }
        }
        return results;
    }

    /**
     * Returns a ArrayList of RequirementBeans associated with the
     * given metadataId.
     * If no requirements are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getRequirements(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "RequirementID, " + "RequirementType, " + "RequirementName", "Requirements", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                RequirementBean rb;
                String id;
                String requirementType, requirementName;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    rb = new RequirementBean();
                    id = row.getString("RequirementID");
                    requirementType = row.getString("RequirementType");
                    requirementName = row.getString("RequirementName");
                    rb.setRequirementId(id);
                    rb.setMetadataId(metadataId);
                    rb.setRequirementType(requirementType);
                    rb.setRequirementName(requirementName);
                    results.add(rb);
                }
            }
        }
        return results;
    }

    /**
     * Returns a TreeMap of ContributorBeans associated with the
     * given metadataId.
     * If no contributors are found or an error occurs, an empty TreeMap is
     * returned.
     */
    public TreeMap getContributors(String metadataId, Connection conn)
            throws SQLException {
        TreeMap results = new TreeMap();
        if(metadataId != null) {
            // queries the database
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                StringBuffer sql = new StringBuffer();
                sql.append("SELECT a.ContributorID, a.Role, a.ContributeDate,")
                        .append(" a.ContributeDateDescription, b.vCard ");
                sql.append("FROM  Contributors a INNER JOIN");
                sql.append("  vCards b ON a.VCardID = b.vCardID ");
                sql.append("WHERE  (a.MetadataID = ?)");
                ps = conn.prepareStatement(sql.toString());
                ps.setString(1, metadataId);
                rs = ps.executeQuery();
                while(rs.next()) {
                    ContributorBean cb = new ContributorBean();
                    final String id = rs.getString(1);
                    final String role = rs.getString(2);
                    final Date contributeDate = rs.getDate(3);
                    final String contributeDateDescription = rs.getString(4);
                    final String vCard = rs.getString(5);
                    cb.setContributorId(id);
                    cb.setRole(role);
                    cb.setDate(contributeDate);
                    cb.setDateDescription(contributeDateDescription);
                    cb.setVCard(vCard);

                    List roles = (List)results.get(role);
                    if(null == roles) {
                        roles = new ArrayList();
                        results.put(role, roles);
                    }
                    roles.add(cb);
                }
            } finally {
                if(null != rs) {
                    rs.close();
                }
                if(null != ps) {
                    ps.close();
                }
            }
        }
        return results;
    }

    /**
     * Returns a ArrayList of RelationBeans associated with the
     * given metadataId.
     * If no relations are found or an error occurs, an empty ArrayList
     * is returned.
     */
    public ArrayList getRelations(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "RelationID, " + "Resource, Kind, Description", "Relations", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                RelationBean rb;
                String id;
                String resource, kind, description;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    rb = new RelationBean();
                    id = row.getString("RelationID");
                    resource = row.getString("Resource");
                    kind = row.getString("Kind");
                    description = row.getString("Description");
                    rb.setRelationId(id);
                    rb.setMetadataId(metadataId);
                    rb.setResource(resource);
                    rb.setKind(kind);
                    rb.setDescription(description);
                    results.add(rb);
                }
            }
        }
        return results;
    }

    /**
     * Returns an ArrayList of KeywordBeans associated with the
     * given metadataId.
     * If no keywords are found or an error occurs, an empty
     * ArrayList is returned.
     */
    public ArrayList getKeywords(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "KeywordID, Keyword", "Keywords", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                KeywordBean keyword;
                String keywordId;
                String keywordString;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    keyword = new KeywordBean();
                    keywordId = row.getString("KeywordID");
                    keywordString = row.getString("Keyword");
                    keyword.setKeywordId(keywordId);
                    keyword.setMetadataId(metadataId);
                    keyword.setKeyword(keywordString);
                    results.add(keyword);
                }
            }
        }
        return results;
    }

    /**
     * Returns an ArrayList of FormatBeans associated with the given
     * metadataId.
     * If no format is found or an error occurs, an empty ArrayList
     * is returned.
     */
    public ArrayList getFormats(String metadataId, Connection conn) throws SQLException, NoSuchColumnException {
        ArrayList results = new ArrayList();
        if(metadataId != null) {
            Vector rows = cd.getMetadataProperties(metadataId, "FormatID, Format", "Formats", conn);
            if(rows != null && rows.size() > 0) {
                Iterator rowIterator = rows.iterator();
                Row row;
                FormatBean format;
                String formatId;
                String formatString;
                while(rowIterator.hasNext()) {
                    row = (Row)rowIterator.next();
                    format = new FormatBean();
                    formatId = row.getString("FormatID");
                    formatString = row.getString("Format");
                    format.setFormatId(formatId);
                    format.setMetadataId(metadataId);
                    format.setFormat(formatString);
                    results.add(format);
                }
            }
        }
        return results;
    }


    /**
     * Returns the first metadataId returned when the Metadata table is
     * queried for all entries with the given value for the given column.
     */
    public String getMetadataIdFromProperty(String columnValue, String columnName, Connection conn)
            throws SQLException {
        if(columnName == null || columnValue == null) {
            return null;
        }
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MetadataID FROM Metadata ")
                .append("WHERE " + columnName + " = ? ORDER BY MetadataID DESC");
        sqlCommandBean.setSqlValue(sql.toString());
        Vector values = new Vector();
        values.addElement(new StringValue(columnValue));
        sqlCommandBean.setValues(values);
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        }
        if(rows == null || rows.size() == 0) {
            // nothing found
            return null;
        }
        Row aRow = (Row)rows.firstElement();
        try {
            String metadataId = aRow.getString("MetadataID");
            return metadataId;
        } catch(NoSuchColumnException nsce) {
            throw new SQLException(nsce.toString());
        }
    }

    /**
     * Returns the first metadataId returned when the Metadata table is
     * queried for all entries with the given value for the given column.
     */
    public String getMetadataIdFromProperty(String columnValue, String columnName) throws SQLException {
        Connection conn = dataSource.getConnection();
        String metadataId = null;
        try {
            metadataId = getMetadataIdFromProperty(columnValue, columnName, conn);
        } finally {
            try {
                conn.close();
            } catch(SQLException ex2) {
                //ignore
            }
        }
        return metadataId;
    }

    /**
     * Returns a list of content Ids in an ArrayList.  The parameter specifies
     * a date used to request all new metadata since the given time.
     * If the provided date is null, all content Ids are returned.
     */
    public ArrayList getPointerListArrayList(java.util.Date lastReceivedDate) throws SQLException {
        return getPointerList(lastReceivedDate, false);
    }

    /**
     * Returns a list of content Ids in XML format.  The parameter specifies
     * a date used to request all new metadata since the given time.
     * If the provided date is null, all content Ids are returned.
     */
    public String getPointerList(java.util.Date lastReceivedDate) throws SQLException, IOException {
        ArrayList ids = getPointerList(lastReceivedDate, false);
        return HealMetadataXMLConverter.getPointerListXML(ids);
    }

    /**
     * Returns a list of content Ids in an ArrayList.  The parameter specifies
     * a date used to request all new metadata since the given time.
     * If the provided date is null, all content Ids are returned.
     * If the includeHidden parameter is set to true, all metadata Ids are
     * returned, not just those set as visible.  If the parameter
     * set to false, then only those set as "visible" will be returned.
     */
    public ArrayList getPointerListArrayList(java.util.Date lastReceivedDate, boolean includeHidden)
            throws SQLException {
        return getPointerList(lastReceivedDate, includeHidden);
    }

    /**
     * Returns a list of content Ids in an ArrayList.  The parameter specifies
     * a date used to request all new metadata since the given time.
     * If the provided date is null, all content Ids are returned.
     * Depending upon the second parameter, this method will return a list
     * containing only metadata not marked as hidden if false, and all
     * metadata Ids if the includeHidden parameter is set to true.
     */
    public ArrayList getPointerList(java.util.Date lastReceivedDate, boolean includeHidden) throws SQLException {
        ArrayList ids = null;
        Connection conn = dataSource.getConnection();
        SQLCommandBean sqlCommandBean = new SQLCommandBean();
        sqlCommandBean.setConnection(conn);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MetadataID FROM Metadata");
        Vector values = new Vector();
        if(lastReceivedDate != null) {
            Timestamp lastReceivedTimestamp = new Timestamp(lastReceivedDate.getTime());
            values.addElement(new TimestampValue(lastReceivedTimestamp));
            if(!includeHidden) {
                sql.append(" WHERE CatalogDate >= ? AND Private = ?");
                values.addElement(new BooleanValue(includeHidden));
            } else {
                //if we want to show all, then don't filter Private setting
                sql.append(" WHERE CatalogDate >= ?");
            }
            sqlCommandBean.setValues(values);
        } else if(!includeHidden) {
            //we only want to screen if we are EXCLUDING ids(don't show hidden)
            sql.append(" WHERE Private = ?");
            values.addElement(new BooleanValue(includeHidden));
            sqlCommandBean.setValues(values);
        }
        sqlCommandBean.setSqlValue(sql.toString());
        Vector rows = null;
        try {
            rows = sqlCommandBean.executeQuery();
            if(rows != null && rows.size() > 0) {
                ids = new ArrayList();
                Iterator rowIter = rows.iterator();
                while(rowIter.hasNext()) {
                    String id = ((Row)rowIter.next()).getString("MetadataID");
                    ids.add(id);
                }
            }
        } catch(UnsupportedTypeException e) {
            throw new SQLException(e.toString());
        } catch(NoSuchColumnException e2) {
            throw new SQLException(e2.toString());
        } // Can not happen here
        finally {
            try {
                conn.close();
            } catch(SQLException e) {
            } // Ignore
        }
        if(rows == null || rows.size() == 0) {
            // Metadata not found
            return null;
        }
        //cycle through all of the rows and pull out the MetadataIDs
        return ids;
    }

    /**
     * Used by {@link org.heal.servlet.cataloger.SaveMetadataAction SaveMetadataAction}
     * so that we can manage a database transaction when updating old records.  This method
     * is somewhat specific to SaveMetadataAction.
     *
     * @param cmb The {@link org.heal.module.metadata.CompleteMetadataBean CompleteMetadataBean}
     * generated from form data.
     */
    public void saveEditMetadataForm(CompleteMetadataBean cmb) throws SQLException {
        final String metadataId = cmb.getMetadataId();

        Set contextURLExcludes = new HashSet();
        for(Iterator iter = cmb.getContextURLs().iterator(); iter.hasNext();) {
            final ContextURLBean cub = (ContextURLBean)iter.next();
            if(null != cub && null != cub.getContextURLId()) {
                contextURLExcludes.add(cub.getContextURLId());
            }
        }

        Set contributorExcludes = new HashSet();
        for(Iterator iter = cmb.getContributorList().iterator(); iter.hasNext();) {
            final ContributorBean cb = (ContributorBean)iter.next();
            if(null != cb.getContributorId()) {
                contributorExcludes.add(cb.getContributorId());
            }
        }

        Set copyrightHolderExcludes = new HashSet();
        for(Iterator iter = cmb.getCopyrightHolders().iterator(); iter.hasNext();) {
            final CopyrightHolderBean chb = (CopyrightHolderBean)iter.next();
            if(null != chb.getCopyrightHolderId()) {
                copyrightHolderExcludes.add(chb.getCopyrightHolderId());
            }
        }

        Set taxonPathExcludes = new HashSet();
        for(Iterator iter = cmb.getTaxonPaths().iterator(); iter.hasNext();) {
            final TaxonPathBean tpb = (TaxonPathBean)iter.next();
            if(null != tpb.getTaxonPathId()) {
                taxonPathExcludes.add(tpb.getTaxonPathId());
            }
        }

        Set requirementExcludes = new HashSet();
        for(Iterator iter = cmb.getRequirements().iterator(); iter.hasNext();) {
            final RequirementBean rb = (RequirementBean)iter.next();
            if(null != rb.getRequirementId()) {
                requirementExcludes.add(rb.getRequirementId());
            }
        }

        Set relationExcludes = new HashSet();
        for(Iterator iter = cmb.getRelations().iterator(); iter.hasNext();) {
            final RelationBean rb = (RelationBean)iter.next();
            if(null != rb.getRelationId()) {
                relationExcludes.add(rb.getRelationId());
            }
        }

        Set mmIdentifierExcludes = new HashSet();
        for(Object o : cmb.getMetametadataIdentifiers()) {
            final MetametadataIdentifierBean mib = (MetametadataIdentifierBean)o;
            if(null != mib.getMetametadataIdentifierId()) {
                mmIdentifierExcludes.add(mib.getMetametadataIdentifierId());
            }
        }

        Set mmContributorExcludes = new HashSet();
        for(Object o : cmb.getMetametadataContributors()) {
            final MetametadataContributorBean mcb = (MetametadataContributorBean)o;
            if(null != mcb.getMetametadataContributorId()) {
                mmContributorExcludes.add(mcb.getMetametadataContributorId());
            }
        }

        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            if(null != metadataId) {
                // If this is an existing metadata record, we must
                // change the values in the database currently to
                // match the CompleteMetadataBean submitted from the
                // edit page
                cd.removeMetadataFromTable(metadataId, "Keywords", conn);
                cd.removeMetadataFromTable(metadataId, "Formats", conn);
                cd.removeMetadataFromTable(metadataId, "DiseaseDiagnoses", conn);
                cd.removeMetadataFromTable(metadataId, "TargetUserGroups", conn);
                cd.removeMetadataFromTable(metadataId, "Thumbnails", conn);
                cd.removeMetadataFromTable(metadataId, "Copyrights", conn);
                deleteForeignKeyRecordsNotInSet("ContextURLs", "MetadataID", "ContextURLID",
                        metadataId, contextURLExcludes, conn);
                deleteForeignKeyRecordsNotInSet("Contributors", "MetadataID", "ContributorID",
                        metadataId, contributorExcludes, conn);
                deleteForeignKeyRecordsNotInSet("CopyrightHolders", "MetadataID",
                        "CopyrightHolderID", metadataId, copyrightHolderExcludes, conn);
                deleteForeignKeyRecordsNotInSet("TaxonPaths", "MetadataID", "TaxonPathID",
                        metadataId, taxonPathExcludes, conn);
                deleteForeignKeyRecordsNotInSet("Requirements", "MetadataID", "RequirementID",
                        metadataId, requirementExcludes, conn);
                deleteForeignKeyRecordsNotInSet("Relations", "MetadataID", "RelationID",
                        metadataId, relationExcludes, conn);
                deleteForeignKeyRecordsNotInSet("MetametadataIdentifiers", "MetadataID",
                        "MetametadataIdentifierID", metadataId, mmIdentifierExcludes, conn);
                deleteForeignKeyRecordsNotInSet("MetametadataContributors", "MetadataID",
                        "MetametadataContributorID", metadataId, mmContributorExcludes, conn);
                for(Iterator iter = cmb.getTaxonPaths().iterator(); iter.hasNext();) {
                    final TaxonPathBean tpb = (TaxonPathBean)iter.next();
                    final String taxonPathId = tpb.getTaxonPathId();
                    if(null != taxonPathId) {
                        Set taxonExcludes = new HashSet();
                        for(Iterator iterTwo = tpb.getTaxons().iterator(); iterTwo.hasNext();) {
                            final TaxonBean tb = (TaxonBean)iterTwo.next();
                            if(null != tb.getTaxonId()) {
                                taxonExcludes.add(tb.getTaxonId());
                            }
                        }

                        deleteForeignKeyRecordsNotInSet("Taxons", "TaxonPathID", "TaxonID",
                                taxonPathId, taxonExcludes, conn);
                    }
                }
            }

            // Cleaning up pieces of the metadata which are empty, and therefor
            // should be null when inserted in the database...
            if(isEmpty(cmb.getGlobalId())) {
                cmb.setGlobalId(null);
            }
            if(isEmpty(cmb.getSpecimenType())) {
                cmb.setSpecimenType(null);
            }
            if(isEmpty(cmb.getRadiographType())) {
                cmb.setRadiographType(null);
            }
            if(isEmpty(cmb.getOrientation())) {
                cmb.setOrientation(null);
            }
            if(isEmpty(cmb.getMagnification())) {
                cmb.setMagnification(null);
            }
            if(isEmpty(cmb.getClinicalHistory())) {
                cmb.setClinicalHistory(null);
            }
            if(null != cmb.getThumbnail()) {
                final ThumbnailBean tb = cmb.getThumbnail();
                if(isEmpty(tb.getFileHeight()) && isEmpty(tb.getFileWidth()) && isEmpty(tb.getLocation())) {
                    cmb.setThumbnail(null);
                }
            }

            saveCompleteMetadata(cmb, conn);
            conn.commit();
        } catch(SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    private static boolean isEmpty(String str) {
        boolean empty = false;
        if(null == str || str.trim().length() == 0) {
            empty = true;
        }
        return empty;
    }


    /**
     * Deletes records from a table that match a given foreign key and which
     * do not have ids that are found in a <code>Set</code>.
     *
     * @param tableName The name of the table to delete records from.
     * @param foreignKeyFieldName The name of the field which acts as a foreign key in the table.
     * @param excludeFieldName The name of the field to match id's to exclude from the deletion.
     * @param foreignKey The foreign key value associated with the records to delete.
     * @param excludes A set of Strings representing ids of records that should
     * not be deleted.
     * @param conn The <code>Connection</code> to the database.
     *
     * @throws SQLException
     */
    public void deleteForeignKeyRecordsNotInSet(String tableName, String foreignKeyFieldName,
                                                String excludeFieldName, String foreignKey,
                                                Set excludes, Connection conn)
            throws SQLException {
        String sql;
        if(0 < excludes.size()) {
            StringBuffer exclude = new StringBuffer("(");
            for(int i = 0; i < excludes.size(); ++i) {
                exclude.append("?");
                if(i < (excludes.size() - 1)) {
                    exclude.append(", ");
                }
            }
            exclude.append(")");
            sql = "DELETE FROM " + tableName + " WHERE " + foreignKeyFieldName + " = ? AND "
                    + excludeFieldName + " NOT IN " + exclude.toString();
        } else {
            sql = "DELETE FROM " + tableName + " WHERE " + foreignKeyFieldName + "= ?";
        }

        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.setInt(1, Integer.parseInt(foreignKey));
            int nextParameter = 2;
            for(Iterator iter = excludes.iterator(); iter.hasNext();) {
                final String exclude = (String)iter.next();
                ps.setInt(nextParameter, Integer.parseInt(exclude));
                ++nextParameter;
            }
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    public int savePublication(String id, String name, Date publicationDate) throws SQLException {
        int ret = 0;
        Connection conn = dataSource.getConnection();
        try {
            ret = savePublication(id, name, publicationDate, dataSource.getConnection());
        } finally {
            if(null != conn) {
                conn.close();
            }
        }
        return ret;
    }

    public int savePublication(String id, String name, Date publicationDate, Connection conn) throws SQLException {
        Integer ret = null;
        try {
            ret = new Integer(Integer.parseInt(id));
        } catch(NumberFormatException e) {
            // does nothing
        }

        String sql;
        if(null == ret) {
            sql = PUBLICATIONINSERTSQL;
        } else {
            sql = PUBLICATIONUPDATESQL;
        }

        java.sql.Date publicationSqlDate = null;
        if(null != publicationDate) {
            publicationSqlDate = new java.sql.Date(publicationDate.getTime());
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDate(2, publicationSqlDate);
            if(null != ret) {
                ps.setInt(3, ret.intValue());
            }
            ps.executeUpdate();

            if(null == ret) {
                ps.close();
                ps = conn.prepareStatement("SELECT MAX(PublicationId) AS uid FROM Publications");
                rs = ps.executeQuery();
                if(rs.next()) {
                    ret = new Integer(rs.getInt(1));
                }
            }
        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }
        if(null == ret) {
            throw new RuntimeException("Unable to save publication: ('" + id + "', '" + name + "', '" + publicationDate.toString() + "')");
        }

        return ret.intValue();
    }

    public void saveMetametadataIdentifier(MetametadataIdentifierBean mib, Connection conn) throws SQLException {
        final String sql;
        if(null == mib.getMetametadataIdentifierId()) {
            sql = METAMETADATA_IDENTIFIER_INSERT_SQL;
        } else {
            sql = METAMETADATA_IDENTIFIER_UPDATE_SQL;
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, mib.getMetadataId());
            ps.setString(2, mib.getCatalog());
            ps.setString(3, mib.getEntry());
            ps.setString(4, mib.getMetadataSchema());

            if(null != mib.getMetametadataIdentifierId()) {
                ps.setString(5, mib.getMetametadataIdentifierId());
            }
            ps.executeUpdate();

            if(null == mib.getMetametadataIdentifierId()) {
                ps.close();
                ps = conn.prepareStatement("SELECT @@Identity AS ID");
                rs = ps.executeQuery();
                rs.next();
                mib.setMetametadataIdentifierId(rs.getString(1));
            }
        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }
    }

    public void saveMetametadataContributor(MetametadataContributorBean mcb) throws SQLException {
        // Save the metadata info from the database
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            saveMetametadataContributor(mcb, conn);
            conn.commit();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void saveMetametadataIdentifier(MetametadataIdentifierBean mcb) throws SQLException {
        // Save the metadata info from the database
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        try {
            saveMetametadataIdentifier(mcb, conn);
            conn.commit();
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }


    /**
     * May have the side-effect of saving a vcard to the vCards table.
     *
     * @param mcb
     * @param conn
     * @throws SQLException
     */
    public void saveMetametadataContributor(MetametadataContributorBean mcb, Connection conn) throws SQLException {
        final String sql;
        if(null == mcb.getMetametadataContributorId()) {
            sql = METAMETADATA_CONTRIBUTOR_INSERT_SQL;
        } else {
            sql = METAMETADATA_CONTRIBUTOR_UPDATE_SQL;
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String vcardId = cd.saveVCard(mcb.getvCard(), conn);

            java.sql.Date date = null;
            if(null != mcb.getDate()) {
                date = new java.sql.Date(mcb.getDate().getTime());
            }

            ps = conn.prepareStatement(sql);
            ps.setString(1, mcb.getMetadataId());
            ps.setString(2, mcb.getRole());
            ps.setDate(3, date);
            ps.setString(4, mcb.getDateDescription());
            ps.setString(5, vcardId);

            if(null != mcb.getMetametadataContributorId()) {
                ps.setString(6, mcb.getMetametadataContributorId());
            }

            ps.executeUpdate();

            if(null == mcb.getMetametadataContributorId()) {
                ps.close();
                ps = conn.prepareStatement("SELECT @@Identity AS ID");
                rs = ps.executeQuery();
                rs.next();
                mcb.setMetametadataContributorId(rs.getString(1));
            }


        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != ps) {
                ps.close();
            }
        }
    }

   /**
     * Returns the count of total resources in the database
     * Returns null on an error or if no entries exist in the
     * database.
     */
    public int getCountOfResources() throws SQLException {
        //Get the usernames from the database
        Connection conn = dataSource.getConnection();
                int count = 0;
        try {
            SQLCommandBean sqlCommandBean = new SQLCommandBean();
            sqlCommandBean.setConnection(conn);
            String sql = "SELECT Count(metadataId) as Expr1 FROM Metadata where private = 0";
            sqlCommandBean.setSqlValue(sql);
            try {
                            Vector rows = null;
                            Row row = null;
                            rows = sqlCommandBean.executeQuery();
                            Iterator rowIterator = rows.iterator();
                            while (rowIterator.hasNext())
                            {
                                row = (Row) rowIterator.next();
                                count = row.getInt("Expr1");
                            }
            } catch (UnsupportedTypeException e) {
                throw new SQLException(e.toString());
            } catch (NoSuchColumnException e2) {
                throw new SQLException(e2.toString());
            }
                             catch (UnsupportedConversionException ex)
                        {
                                throw new SQLException(ex.toString());
                        }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            } // Ignore
        }
        return count;
    }


}
