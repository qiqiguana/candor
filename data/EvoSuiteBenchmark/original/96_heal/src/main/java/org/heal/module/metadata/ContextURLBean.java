package org.heal.module.metadata;

import java.io.Serializable;

/**
 * This class contains information about a contextURL.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class ContextURLBean implements Serializable {
    // Properties
    private String contextURLId;
    private String metadataId;
    private String contextURL;
    private String contextURLDescription;

    /**
     * Returns the contextURLId property value.
     */
    public String getContextURLId() {
	return this.contextURLId;
    }

    /**
     * Sets the contextURLId property value.
     */
    public void setContextURLId(String newContextURLId) {
	this.contextURLId = newContextURLId;
    }

    /**
     * Returns the metadataId property value.
     */
    public String getMetadataId() {
	return this.metadataId;
    }

    /**
     * Sets the metadataId property value.
     */
    public void setMetadataId(String newMetadataId) {
	this.metadataId = newMetadataId;
    }

    /**
     * Returns the contextURL property value.
     */
    public String getContextURL() {
	return this.contextURL;
    }

    /**
     * Sets the contextURL property value.
     */
    public void setContextURL(String newContextURL) {
	this.contextURL = newContextURL;
    }

    /**
     * Returns the contextURLDescription property value.
     */
    public String getContextURLDescription() {
        return contextURLDescription;
    }

    /**
     * Sets the contextURLDescription property value.
     */
    public void setContextURLDescription(String contextURLDescription) {
        this.contextURLDescription = contextURLDescription;
    }

    public String toString() {
	return "ContextURL: ID:"+contextURLId+
	    " metadataId:"+metadataId+
	    " contextURL:"+contextURL+
        " contextURLDescription:"+contextURLDescription;
    }
}





