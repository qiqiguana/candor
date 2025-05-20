package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a requirement.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class RequirementBean implements Serializable {
    // Properties
    public static final String BROWSER = "Web Browser";
    public static final String OS = "Operating System";
    private String requirementId = null;
    private String metadataId = null;
    private String requirementType = null;
    private String requirementName = null;
    private String otherPlatform = null;
    private String duration = null;
    private String description = null;

    /**
     * Returns the requirementId property value.
     */
    public String getRequirementId() {
	return this.requirementId;
    }

    /**
     * Sets the requirementId property value.
     */
    public void setRequirementId(String newRequirementId) {
	this.requirementId = newRequirementId;
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
     * Returns the requirementType property value.
     */
    public String getRequirementType() {
	return this.requirementType;
    }

    /**
     * Sets the requirementType property value.
     */
    public void setRequirementType(String newRequirementType) {
	this.requirementType = newRequirementType;
    }

    /**
     * Returns the requirementName property value.
     */
    public String getRequirementName() {
	return this.requirementName;
    }

    /**
     * Sets the requirementName property value.
     */
    public void setRequirementName(String newRequirementName) {
	this.requirementName = newRequirementName;
    }
    /**
     * Returns the otherPlatform requirement property value.
     */
    public String getOtherPlatform() {
        return this.otherPlatform;
    }

    /**
     * Sets the otherPlatform property value.
     */
    public void setOtherPlatform(String newotherPlatform) {
        this.otherPlatform = newotherPlatform;
    }
    /**
     * Returns the duration property value.
     */
    public String getDuration() {
        return this.duration;
    }

    /**
     * Sets the duration property value.
     */
    public void setDuration(String newduration) {
        this.duration = newduration;
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
    public void setDescription(String newdescription) {
        this.description = newdescription;
    }

    public String toString() {
	return "Requirement: ID:"+requirementId+
	    " metadataId:"+metadataId+
	    " requirementType:"+requirementType+
	    " requirementName:"+requirementName;
    }
}





