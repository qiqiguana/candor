package org.heal.module.metadata;

import java.io.*;

/**
 * This class contains information about a thumbnail image for
 * a metadata entry.
 *
 * @author Grace
 * @version 0.1
 */
public class SourceCollectionBean implements Serializable 
{
  // Properties
  private String sourceId = null;
  private String name = null;
  private String location = null;
  private String fileWidth = null;
  private String fileHeight = null;
  private String link=null;

  /**
    * Returns the source collection property value.
    */
  public String getSourceId()
  {
    return this.sourceId;
  }
  /**
    * Sets the source collection property value.
    */
  public void setSourceId(String sId) 
  {
    this.sourceId = sId;
  }
  /**
    * Returns the source collection property value.
    */
  public String getSourceName() 
  {
    return this.name;
  }
  /**
    * Sets the source collection property value.
    */
  public void setSourceName(String sname) 
  {
    this.name = sname;
  }
  /**
    * Returns the location property value.
    */
  public String getLocation() 
  {
    return this.location;
  }
  /**
    * Sets the location property value.
    */
  public void setLocation(String newLocation) 
  {
    this.location = newLocation;
  }
  /**
    * Returns the fileWidth property value.
    */
  public String getFileWidth() 
  {
    return this.fileWidth;
  }
  /**
    * Sets the fileWidth property value.
    */
  public void setFileWidth(String newFileWidth) 
  {
    this.fileWidth = newFileWidth;
  }
  /**
    * Returns the fileHeight property value.
    */
  public String getFileHeight() 
  {
    return this.fileHeight;
  }
  /**
    * Returns the link property value.
    */
  public String getLink() 
  {
    return this.link;
  }
  /**
    * Sets the link property value.
    */
  public void setLink(String newLink) 
  {
    this.link = newLink;
  }
  /**
    * Sets the fileHeight property value.
    */
  public void setFileHeight(String newFileHeight) 
  {
    this.fileHeight = newFileHeight;
  }
  public String toString() 
  {
    return "Source:SourceId:"+sourceId+
	    " name:"+name+
	    " location:"+location+
	    " fileWidth:"+fileWidth+
	    " fileHeight:"+fileHeight+
      " link:"+link;
  }
  public Object clone() 
  {
    SourceCollectionBean result = new SourceCollectionBean();
    result.sourceId= new String(sourceId);
    result.name = new String(name);
    result.location = new String(location);
    result.fileWidth = new String(fileWidth);
    result.fileHeight = new String(fileHeight);
    result.link = new String(link);
    return result;
  }
}





