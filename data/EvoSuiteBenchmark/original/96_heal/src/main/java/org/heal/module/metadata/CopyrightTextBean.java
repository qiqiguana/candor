package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a copyright's text.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class CopyrightTextBean implements Serializable {
    // Properties
    private String copyrightTextId;
    private String copyrightText;
    private String cost;
    private String copyrightAndOtherRestriction;
    /**
     * Returns the copyrightTextId property value.
     */
    public String getCopyrightTextId() {
	return this.copyrightTextId;
    }

    /**
     * Sets the copyrightTextId property value.
     */
    public void setCopyrightTextId(String newCopyrightTextId) {
	this.copyrightTextId = newCopyrightTextId;
    }

    /**
     * Returns the copyrightText property value.
     */
    public String getCopyrightText() {
	return this.copyrightText;
    }

    /**
     * Sets the copyrightText property value.
     */
    public void setCopyrightText(String newCopyrightText) {
	this.copyrightText = newCopyrightText;
    }
    
    public String getCost() {
        return cost;
    }
    
    public void setCost(String cost) {
        this.cost = cost;
    }
    
    public String getCopyrightAndOtherRestriction() {
        return copyrightAndOtherRestriction;
    }
    
    public void setCopyrightAndOtherRestriction(String copyrightAndOtherRestriction) {
        this.copyrightAndOtherRestriction = copyrightAndOtherRestriction;
    }
    
    public String toString() {
	return "CopyrightText: ID:"+copyrightTextId+
	    " copyrightText:"+copyrightText+
            " cost:"+cost+
            " copyrightAndOtherRestriction:"+copyrightAndOtherRestriction;
    }
}





