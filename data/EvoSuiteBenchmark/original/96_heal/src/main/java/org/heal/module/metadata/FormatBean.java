package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a format.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class FormatBean implements Serializable {
    // Properties
    private String formatId;
    private String metadataId;
    private String format;

    /**
     * Returns the formatId property value.
     */
    public String getFormatId() {
	return this.formatId;
    }

    /**
     * Sets the formatId property value.
     */
    public void setFormatId(String newFormatId) {
	this.formatId = newFormatId;
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

    public String toString() {
	return "Format: ID:"+formatId+
	    " metadataId:"+metadataId+
	    " format:"+format;
    }
}





