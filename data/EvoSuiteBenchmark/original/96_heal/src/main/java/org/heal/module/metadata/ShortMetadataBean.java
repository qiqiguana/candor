package org.heal.module.metadata;

import java.io.Serializable;

/**
 * This class contains basic information about heal metadata.
 *
 * @author Seth Wright
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see MetadataBean
 * @see CompleteMetadataBean
 */
public class ShortMetadataBean implements Serializable {
    // Properties
    private String metadataId = null;
    private String globalId = null;
    private String fileName = null;
    private String fileSize = null;
    private String title = null;
    private String description = null;
    private String location = null;
    private String sourceCollection = null;
    private String fileWidth = null;
    private String fileHeight = null;
    private String format = null;
    private String contributeUserId = null;
    private String contributeName = null;
    private ThumbnailBean thumbnail;
    private String learningResourceType = null;
    private SourceCollectionBean sourcecollections = null;

    /**
     * Returns the metadataId property value.
     */
    public String getMetadataId() {
        return this.metadataId;
    }

    /**
     * Sets the metadata property value.
     */
    public void setMetadataId(String newMetadataId) {
        this.metadataId = newMetadataId;
    }

    /**
     * Returns the globalId property value.
     */
    public String getGlobalId() {
        return this.globalId;
    }

    /**
     * Sets the globalId property value.
     */
    public void setGlobalId(String newGlobalId) {
        this.globalId = newGlobalId;
    }

    /**
     * Returns the fileName property value.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Sets the fileName property value.
     */
    public void setFileName(String newFileName) {
        this.fileName = newFileName;
    }

    /**
     * Returns the fileExtension derived from the file name
     * including the period.  For example <code>".jpg"</code>.
     */
    public String getFileExtension() {
        int index;
        if(null != fileName && (index = fileName.lastIndexOf(".")) != -1) {
            return fileName.substring(index + 1);
        }
        return null;
    }

    /**
     * Sets the fileExtension property value.  THIS METHOD NO LONGER CHANGES
     * WHAT IS RETURNED BY {@link #getFileExtension()}.
     *
     * @deprecated File extension is now derived from the filename.
     */
    public void setFileExtension(String newFileExtension) {
    }

    /**
     * Returns the fileSize property value.
     */
    public String getFileSize() {
        return this.fileSize;
    }

    /**
     * Sets the fileSize property value.
     */
    public void setFileSize(String newFileSize) {
        this.fileSize = newFileSize;
    }

    /**
     * Returns the title property value.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title property value.
     */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    /**
     * Returns the description property value.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description property value.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Returns the location property value.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Sets the location property value.
     */
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    /**
     * Returns the sourceCollection property value.
     */
    public String getSourceCollection() {
        return this.sourceCollection;
    }

    /**
     * Sets the sourceCollection property value.
     */
    public void setSourceCollection(String newSourceCollection) {
        this.sourceCollection = newSourceCollection;
    }

    /**
     * Returns the fileWidth property value.
     */
    public String getFileWidth() {
        return this.fileWidth;
    }

    /**
     * Sets the fileWidth property value.
     */
    public void setFileWidth(String newFileWidth) {
        this.fileWidth = newFileWidth;
    }

    /**
     * Returns the fileHeight property value.
     */
    public String getFileHeight() {
        return this.fileHeight;
    }

    /**
     * Sets the fileHeight property value.
     */
    public void setFileHeight(String newFileHeight) {
        this.fileHeight = newFileHeight;
    }

    /**
     * Returns the format property value.
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * Sets the format property value.
     */
    public void setFormat(String newFormat) {
        this.format = newFormat;
    }

    /**
     * Returns the contributeUserId property value.
     */
    public String getContributeUserId() {
        return this.contributeUserId;
    }

    /**
     * Sets the contributeUserId property value.
     */
    public void setContributeUserId(String newContributeUserId) {
        this.contributeUserId = newContributeUserId;
    }

    /**
     * Returns the contributeName property value.
     */
    public String getContributeName() {
        return this.contributeName;
    }

    /**
     * Sets the contributeName property value.
     */
    public void setContributeName(String newContributeName) {
        this.contributeName = newContributeName;
    }

    /**
     * Returns the thumbnail property value.
     */
    public ThumbnailBean getThumbnail() {
        return this.thumbnail;
    }

    /**
     * Sets the thumbnail property value.
     */
    public void setThumbnail(ThumbnailBean newThumbnail) {
        this.thumbnail = newThumbnail;
    }

    /**
     * Returns the learningResourceType property value.
     */
    public String getLearningResourceType() {
        return this.learningResourceType;
    }

    /**
     * Sets the learningResourceType property value.
     */
    public void setLearningResourceType(String learningResourceType) {
        this.learningResourceType = learningResourceType;
    }
    /**
     * Returns the SourceCollection Bean property value.
     */
    public SourceCollectionBean getCollectionBean() 
    {
      return this.sourcecollections;
    }
    /**
     * Sets the SourceCollection Bean property value.
     */
    public void setCollectionBean(SourceCollectionBean cbean)
    {
      this.sourcecollections = cbean;
    }
    public String toString() {
        final String NEWLINE = System.getProperty("line.separator");
        StringBuffer ret = new StringBuffer("Basic metadata:");
        ret.append(NEWLINE);
        ret.append("metadataId: ").append(getMetadataId()).append(NEWLINE);
        ret.append("title: ").append(getTitle()).append(NEWLINE);
        ret.append("description: ").append(getDescription()).append(NEWLINE);
        ret.append("location: ").append(getLocation()).append(NEWLINE);
        ret.append("contributeName: ").append(getContributeName()).append(NEWLINE);
        ret.append("contributeUserId: ").append(getContributeUserId()).append(NEWLINE);
        ret.append("fileName: ").append(getFileName()).append(NEWLINE);
        ret.append("fileExtension: ").append(getFileExtension()).append(NEWLINE);
        ret.append("fileHeight: ").append(getFileHeight()).append(NEWLINE);
        ret.append("fileWidth: ").append(getFileWidth()).append(NEWLINE);
        ret.append("format: ").append(getFormat()).append(NEWLINE);
        ret.append("globalId: ").append(getGlobalId()).append(NEWLINE);
        ret.append("sourceCollection: ").append(getSourceCollection()).append(NEWLINE);
        ret.append("thumbnail: ").append((null != getThumbnail() ? getThumbnail().toString() : null)).append(NEWLINE);
        ret.append("sourcecollections: ").append((null != getCollectionBean() ? getCollectionBean().toString() : null)).append(NEWLINE);
        return ret.toString();
    }
}
