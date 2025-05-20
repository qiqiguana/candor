package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a relation.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class RelationBean implements Serializable {
    // Properties
    private String relationId;
    private String metadataId;
    private String resource;
    private String kind;
    private String description;
    private String catalogue;
    private String entry;

    /**
     * Returns the relationId property value.
     */
    public String getRelationId() {
	return this.relationId;
    }

    /**
     * Sets the relationId property value.
     */
    public void setRelationId(String newRelationId) {
	this.relationId = newRelationId;
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
     * Returns the resource property value.
     */
    public String getResource() {
	return this.resource;
    }

    /**
     * Sets the resource property value.
     */
    public void setResource(String newResource) {
	this.resource = newResource;
    }

    /**
     * Returns the kind property value.
     */
    public String getKind() {
	return this.kind;
    }

    /**
     * Sets the kind property value.
     */
    public void setKind(String newKind) {
	this.kind = newKind;
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
     * Returns the catalogue property value.
     */
    public String getCatalogue() {
        return this.catalogue;
    }
    
    /**
     * Sets the catalogue property value.
     */
    public void setCatalogue(String newCatalogue) {
        this.catalogue = newCatalogue;
    }
    
    /**
     * Returns the entry property value.
     */
    public String getEntry() {
        return this.entry;
    }
    
    /**
     * Sets the entry property value.
     */
    public void setEntry(String newEntry) {
        this.entry = newEntry;
    }

    public String toString() {
	return "Relation: ID:"+relationId+
	    " metadataId:"+metadataId+
	    " resource:"+resource+
	    " kind:"+kind+
	    " description:"+description+
            " catalogue:"+catalogue+
            " entry:"+entry;
    }
}





