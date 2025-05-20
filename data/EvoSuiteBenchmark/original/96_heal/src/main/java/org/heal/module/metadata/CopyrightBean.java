package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a copyright.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class CopyrightBean implements Serializable {
    // Properties
    private String copyrightId;
    private String metadataId;
    private CopyrightTextBean copyrightText;

    /**
     * Returns the copyrightId property value.
     */
    public String getCopyrightId() {
	return this.copyrightId;
    }

    /**
     * Sets the copyrightId property value.
     */
    public void setCopyrightId(String newCopyrightId) {
	this.copyrightId = newCopyrightId;
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
     * Returns the copyrightText property value.
     */
    public CopyrightTextBean getCopyrightText() {
	return this.copyrightText;
    }

    /**
     * Sets the copyrightTextId property value.
     */
    public void setCopyrightText(CopyrightTextBean newCopyrightText) {
	this.copyrightText = newCopyrightText;
    }

    public String toString() {
	return "Copyright: ID:"+copyrightId+
	    " metadataId:"+metadataId+
	    " copyrightText:"+copyrightText.toString();
    }
}
