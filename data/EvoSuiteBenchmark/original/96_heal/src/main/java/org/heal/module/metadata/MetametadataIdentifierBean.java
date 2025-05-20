package org.heal.module.metadata;

import java.io.Serializable;

/**
 * Represents a metametadata identifier for a resource.
 */
public class MetametadataIdentifierBean implements Serializable {
    private String metametadataIdentifierId;
    private String metadataId;
    private String catalog;
    private String entry;
    private String metadataSchema;


    public MetametadataIdentifierBean() {
    }

    public String getMetametadataIdentifierId() {
        return metametadataIdentifierId;
    }

    public void setMetametadataIdentifierId(String metametadataIdentifierId) {
        this.metametadataIdentifierId = metametadataIdentifierId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
    
    public String getMetadataSchema() {
        return metadataSchema;
    }
    
    public void setMetadataSchema(String metadataSchema) {
        this.metadataSchema = metadataSchema;
    }

    public String toString() {
        return "MetametadataIdentifierBean{" +
                "metametadataIdentifierId='" + metametadataIdentifierId + "'" +
                ", metadataId='" + metadataId + "'" +
                ", catalog='" + catalog + "'" +
                ", entry='" + entry + "'" +
                ", metadataSchema='" + metadataSchema + "'" +
                "}";
    }
}
