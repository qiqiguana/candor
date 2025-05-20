package org.heal.module.metadata;

import java.io.Serializable;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class contains information about a taxonPath.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class TaxonPathBean implements Serializable {
    // Properties
    private String taxonPathId;
    private String metadataId;
    private String source;
    private String purpose;
    private String description;
    private String keyword;
    private SortedSet taxons = new TreeSet();

    /**
     * Returns the taxonPathId property value.
     */
    public String getTaxonPathId() {
        return taxonPathId;
    }

    /**
     * Sets the taxonPathId property value.
     */
    public void setTaxonPathId(String newTaxonPathId) {
        taxonPathId = newTaxonPathId;
    }

    /**
     * Returns the metadataId property value.
     */
    public String getMetadataId() {
        return metadataId;
    }

    /**
     * Sets the metadataId property value.
     */
    public void setMetadataId(String newMetadataId) {
        metadataId = newMetadataId;
    }

    /**
     * Returns the source property value.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source property value.
     */
    public void setSource(String newSource) {
        source = newSource;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * Returns the taxons property value.
     */
    public SortedSet getTaxons() {
        return taxons;
    }

    /**
     * Sets the taxons property value.
     */
    public void setTaxons(SortedSet newTaxons) {
        taxons = newTaxons;
    }

    /**
     * Adds a taxon to the sorted set of taxons.
     */
    public void addTaxon(TaxonBean newTaxon) {
        taxons.add(newTaxon);
    }
    
    

    public String toString() {
        String taxonStr = null;
        if(taxons != null) {
            StringBuffer sbuff = new StringBuffer();
            Iterator iter = taxons.iterator();
            TaxonBean taxon;
            while(iter.hasNext()) {
                taxon = (TaxonBean)iter.next();
                sbuff.append(taxon.toString() + " ");
            }
            taxonStr = sbuff.toString();
        }
        return "TaxonPath: taxonPathID:" + taxonPathId +
                " metadataId:" + metadataId + " source:" + source + " purpose:" + purpose + " description:" + description + " keyword:" + keyword +" taxons:" + taxonStr;
    }
}





