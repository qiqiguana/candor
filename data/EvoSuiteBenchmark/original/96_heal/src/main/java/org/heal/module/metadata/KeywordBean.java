package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a keyword.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class KeywordBean implements Serializable {
    // Properties
    private String keywordId;
    private String metadataId;
    private String keyword;

    /**
     * Returns the keywordId property value.
     */
    public String getKeywordId() {
	return this.keywordId;
    }

    /**
     * Sets the keywordId property value.
     */
    public void setKeywordId(String newKeywordId) {
	this.keywordId = newKeywordId;
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
     * Returns the keyword property value.
     */
    public String getKeyword() {
	return this.keyword;
    }

    /**
     * Sets the keyword property value.
     */
    public void setKeyword(String newKeyword) {
	this.keyword = newKeyword;
    }

    public String toString() {
	return "Keyword: ID:"+keywordId+
	    " metadataId:"+metadataId+
	    " keyword:"+keyword;
    }
}





