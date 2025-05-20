package org.asphodel.index;

import java.util.Map;
import java.util.HashMap;

/**
 * @author sunwj
 * @since 0.1
 * @version 0.1
 * Date: Apr 7, 2007
 * Time: 12:12:58 PM
 * Denoting the indexed object
 */
public class Indexee {

    private IndexeeContent indexeeContent;

    /**
     * the uri through which the original object can be retrieved 
     * */
    private String uri;

    /**
     * other fields need to be storied and will be queried when searching<br/>
     * It holds the collection of
     * such as
     * */
    private Map otherFields;


    public IndexeeContent getIndexeeContent() {
        return indexeeContent;
    }

    public void setIndexeeContent(IndexeeContent indexeeContent) {
        this.indexeeContent = indexeeContent;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map getOtherFields() {
        return otherFields;
    }

    public void setOtherFields(Map otherFields) {
        this.otherFields = otherFields;
    }

    public void addField(String key,Object value)
    {
        if(this.otherFields==null)
        {
            this.setOtherFields(new HashMap());
        }
        this.getOtherFields().put(key,value);
    }
}
