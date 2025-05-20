package org.heal.servlet.tagcloud;

import java.io.Serializable;
import java.util.Date;

/* author Zhen Gu 5/17/08 */

public class TagBean implements Serializable {

    private int tagId;
    private String tag;
    private String metadataId;
    private String userId;
    private Date entryDate;
    private int tagCount;
    private int fontSize;
            
    public int getTagId() {
        return tagId;
    }

    public void setTagId (int newTagId) {
        this.tagId = newTagId;
    }

    public String getTag(){
        return tag;
    }
    
    public void setTag(String newTag){
        this.tag = newTag;
    }
    
    public String getMetadataId(){
        return metadataId;
    }
    
    public void setMetadataId(String newMetadataId){
        this.metadataId = newMetadataId;
    }
    
    public String getUserId(){
        return userId;
    }
        
    public void setUserId(String newUserId){
        this.userId = newUserId;
    }
    
    public Date getEntryDate(){
        return entryDate;
    }
    
    public void setEntryDate(Date newEntryDate){
        this.entryDate = newEntryDate;
    }

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount (int newTagCount) {
        this.tagCount = newTagCount;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize (int newFontSize) {
        this.fontSize = newFontSize;
    }

}
