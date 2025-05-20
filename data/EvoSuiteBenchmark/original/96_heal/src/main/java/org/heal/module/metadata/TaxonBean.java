package org.heal.module.metadata;

import java.io.Serializable;

/**
 * This class contains information about a taxon.
 * This class implements the Comparable interface so that it can be stored
 * in a SortedSet in the TaxonPathBean.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class TaxonBean implements Serializable, Comparable {

    // Properties
    private String taxonId;
    private String taxonPathId;
    private String id;
    private String entry;

    /**
     * Returns the taxonId property value.
     */
    public String getTaxonId() {
        return this.taxonId;
    }

    /**
     * Sets the taxonId property value.
     */
    public void setTaxonId(String newTaxonId) {
        this.taxonId = newTaxonId;
    }

    /**
     * Returns the taxonPathId property value.
     */
    public String getTaxonPathId() {
        return this.taxonPathId;
    }

    /**
     * Sets the taxonPathId property value.
     */
    public void setTaxonPathId(String newTaxonPathId) {
        this.taxonPathId = newTaxonPathId;
    }

    /**
     * Returns the id property value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id property value.
     */
    public void setId(String newId) {
        this.id = newId;
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

    /* compare the IDs and paths of the objects
     * It only makes sense to compare Taxons from the _same_ path.
     */
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj instanceof TaxonBean) {
            TaxonBean tb = (TaxonBean)obj;
            equal = stringEqual(id, tb.id) && stringEqual(taxonId, tb.taxonId)
                  && stringEqual(entry, tb.entry) && stringEqual(taxonPathId, tb.taxonPathId);
        }
        return equal;
    }

    /**
     * Compares two TaxonBeans.  The comparison is done upon
     * the length of the ID field and simply rely's upon the java.lang.String
     * compareTo method.
     * <p/>
     * Note: We don't check the type of the object because if we detect that
     * the types don't match, there is no valid/logical return value we
     * can give to indicate this.  Therefore we rely upon a
     * ClassCastException to be thrown.
     */
    public int compareTo(Object obj)
            throws ClassCastException {
        TaxonBean tb = (TaxonBean)obj;
        int result;
        if(equals(obj)) {
            result = 0;
        } else {
            if(null == id) {
                // Because this is in a TreeSet if !equals(obj), then compareTo cannot return 0
                result = -1;
            } else {
                if(null == tb.id) {
                    result = 1;
                } else {
                    result = id.compareTo(tb.id);

                    if(0 == result) {
                        // Because this is in a TreeSet if !equals(obj), then compareTo cannot return 0
                        result = -1;
                    }
                }
            }
        }
        return result;
    }

    public String toString() {
        return "Taxon: taxonID:" + taxonId + " taxonPathId:" + taxonPathId +
                " ID:" + id + " entry:" + entry;
    }

    private static boolean stringEqual(String one, String two) {
        return ((null == one && null == two) || (null != one && one.equals(two)));
    }
}





