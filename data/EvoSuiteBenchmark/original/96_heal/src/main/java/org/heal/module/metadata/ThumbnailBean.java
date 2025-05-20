package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a thumbnail image for
 * a metadata entry.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class ThumbnailBean implements Serializable {
    // Properties
    private String thumbnailId = null;
    private String metadataId = null;
    private String location = null;
    private String fileWidth = null;
    private String fileHeight = null;

    /**
     * Returns the thumbnailId property value.
     */
    public String getThumbnailId() {
	return this.thumbnailId;
    }

    /**
     * Sets the thumbnail property value.
     */
    public void setThumbnailId(String newThumbnailId) {
	this.thumbnailId = newThumbnailId;
    }

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

    public String toString() {
	return "Thumbnail: ThumbnailId:"+thumbnailId+
	    " MetadataId:"+metadataId+
	    " location:"+location+
	    " fileWidth:"+fileWidth+
	    " fileHeight:"+fileHeight;
    }

    public Object clone() {
	ThumbnailBean result = new ThumbnailBean();
	result.thumbnailId = new String(thumbnailId);
	result.metadataId = new String(metadataId);
	result.location = new String(location);
	result.fileWidth = new String(fileWidth);
	result.fileHeight = new String(fileHeight);
	return result;
    }
}





