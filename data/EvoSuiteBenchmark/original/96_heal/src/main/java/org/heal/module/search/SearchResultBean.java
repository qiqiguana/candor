package org.heal.module.search;

import java.io.Serializable;
import java.util.List;

/**
 * This class contains information about the results of a Metadata search.
 * The SearchResultBean contains an ordered list of
 *
 * @author Seth Wright
 * Modify by Grace: Added setMetaIDs() and getMetaIDs()
 *                  Added setKeywords() and getKeywords()
 * @version 0.1
 */

public class SearchResultBean implements Serializable {
    //an ordered set of ShortMetadataBeans
    private ShortMetadataResultBean[] shortRecords = null;
    //whether or not this was a simple keyword search
    private boolean simple = false;
    //the list of data types filtered against
    private String[] filterList = null;
    //an ordered set of MetadatIDs
    private List metaIDs = null;
    private String keywords = null;

    public ShortMetadataResultBean[] getShortRecords() {
        return shortRecords;
    }

    public void setShortRecords(ShortMetadataResultBean[] newRecords) {
        shortRecords = newRecords;
    }

    public boolean isSimple() {
        return simple;
    }

    public void setSimple(boolean newSimple) {
        simple = newSimple;
    }

	public String[] getFilterList() {
        return filterList;
    }

    public void setFilterList(String[] newFilterList) {
        filterList = newFilterList;
    }

    public void setMetaIDs(List mids) {
        metaIDs = mids;
    }

    public List getMetaIDs() {
        return metaIDs;
    }

    public void setKeywords(String kwds) {
        keywords = kwds;
    }

    public String getKeywords() {
        return keywords;
    }
}
