package org.asphodel.search;

import org.apache.lucene.document.Document;

import java.util.Date;

/**
 * @author sunwj
 * @version 0.1
 *          Date: Apr 7, 2007
 *          Time: 10:30:06 PM
 * @since 0.1
 *
 * represents one search record
 */
public class FtrRecord {
    private Document document;
    private float score;
    private String brief;//(with highlight)
    
    //the original uri
    private String uri;
    private Date cachedDate;


    FtrRecord(Document document,float score) {
        this.document = document;
        this.score = score;
    }

    public Object getField(String fieldName) {
        return document.getField(fieldName).stringValue();
    }


    public Document getDocument() {
        return document;
    }

    public float getBoost() {
        return document.getBoost();
    }


    public float getScore() {
        return score;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getCachedDate() {
        return cachedDate;
    }

    public void setCachedDate(Date cachedDate) {
        this.cachedDate = cachedDate;
    }
}
