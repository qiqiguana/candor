package org.asphodel.search;

import java.util.Collection;
import java.util.ArrayList;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 * Date: Apr 7, 2007
 * Time: 10:27:28 PM
 * FtrSearchResult holds the result of the current search,includes result items,<br/>
 * and others
 */
public class FtrSearchResult {
    private Collection<FtrRecord> ftrRecords;
    private int total;

    public void addFtrRecord(FtrRecord ftrRecord) {
        if (this.getFtrRecords() == null) {
            this.ftrRecords = new ArrayList();
        }
        this.ftrRecords.add(ftrRecord);
    }

    public Collection<FtrRecord> getFtrRecords() {
        return ftrRecords;
    }

    public void setFtrRecords(Collection<FtrRecord> ftrRecords) {
        this.ftrRecords = ftrRecords;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
