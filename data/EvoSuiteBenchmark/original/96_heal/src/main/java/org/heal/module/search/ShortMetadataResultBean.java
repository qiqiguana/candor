package org.heal.module.search;

import org.heal.module.metadata.ShortMetadataBean;

import java.io.Serializable;

/**
 * This class contains information about the results of a Metadata search.
 * The SearchResultBean contains an ordered list of
 *
 * @author Seth Wright
 * @version 0.1
 */
public class ShortMetadataResultBean implements Serializable, Comparable {
    //the greater the match string, the better the match
    private int matchStrength = 0;
    private int keywordsMatched = 0;
    private String lastKeywordMatched = null;
    private String metadataId = null;
    private ShortMetadataBean shortMetadata = null;

    public void setMatchStrength(int newStrength) {
        matchStrength = newStrength;
    }

    public int getMatchStrength() {
        return matchStrength;
    }

    public void incrementMatchStrength(int incrementAmount) {
        matchStrength += incrementAmount;
    }

    public void setKeywordsMatched(int newKeywordsMatched) {
        keywordsMatched = newKeywordsMatched;
    }

    public void incrementKeywordsMatched(int incrementAmount) {
        keywordsMatched += incrementAmount;
    }

    public int getKeywordsMatched() {
        return keywordsMatched;
    }

    public void setLastKeywordMatched(String newLastKeywordMatched) {
        lastKeywordMatched = newLastKeywordMatched;
    }

    public String getLastKeywordMatched() {
        return lastKeywordMatched;
    }

    public void setMetadataId(String newMetadataId) {
        metadataId = newMetadataId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setShortMetadata(ShortMetadataBean newShortMetadata) {
        shortMetadata = newShortMetadata;
    }

    public ShortMetadataBean getShortMetadata() {
        return shortMetadata;
    }

    /* compare the metadataIds of the objects
     * It only makes sense to compare ShortMetadataResultBeans from the _same_
     * search, so in theory the matchStrength of two results with the same
     * metadataId should be identical.  Thus we only need to compare the
     * metadataIds.
     */
    public boolean equals(Object obj) {
        boolean equal = false;
        if(obj instanceof ShortMetadataResultBean) {
            ShortMetadataResultBean sr = (ShortMetadataResultBean)obj;
            if(metadataId.equals(sr.metadataId)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Compares two ShortMetadataResultBeans.  The comparison is done upon
     * the strength of the match.  If the passed in object has a stronger
     * match a negative value will be returned, if the strengths are equal
     * zero is returned, and if this object has a greater match strength
     * than the passed in object, a positive value is returned.
     *
     * Note: We don't check the type of the object because if we detect that
     * the types don't match, there is no valid/logical return value we
     * can give to indicate this.  Therefore we rely upon a
     * ClassCastException to be thrown.
     *
     * We switch the order of the comparisons here because we want the first
     * elements in a sorted collection to be those with the largest
     * matchStrengths.
     */
    public int compareTo(Object obj) throws ClassCastException {
        ShortMetadataResultBean smr = (ShortMetadataResultBean)obj;
        if(smr.matchStrength > matchStrength) {
            return 1;
        } else if(smr.matchStrength == matchStrength) {
            try {
                int smrId = Integer.parseInt(smr.metadataId);
                int id = Integer.parseInt(metadataId);
                if(id > smrId) {
                    return 1;
                } else if(id == smrId) {
                    return 0;
                } else {
                    return -1;
                }
            } catch(NumberFormatException ex) {
                //should not happen
                return 0;  //??? - do some logging
            }
        } else {
            return -1;
        }
    }
}
