package org.heal.util;

import java.io.*;
import java.util.*;
import org.heal.module.metadata.*;
import org.heal.module.user.*;
import java.sql.SQLException;

/**
 * The creation of this class is intended to remove much of the java
 * code from the JSP pages by providing methods to get such things as
 * thumbnail images, etc.  This should be created in the controller
 * servlet and then exposed to the application as 
 * "interfaceUtilitiesBean" in the application scope. 
 *
 * @author Seth Wright
 * @version 0.1
 */
public class InterfaceUtilitiesBean implements Serializable {

    private HashMap thumbnailMap = new HashMap();
    private ThumbnailBean unknownThumbnail;
    private FileLocator fileLocator = null;

    
    public void init(FileLocator locator) {
	fileLocator = locator;
	ThumbnailBean thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_animation.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("animation",thumbnail);
	thumbnailMap.put("application/x-shockwave-flash",thumbnail);
  
	
	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_audio.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("audio",thumbnail);

	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_website.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("web page",thumbnail);
	thumbnailMap.put("text/html",thumbnail);
  

	
	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_presentation.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("presentation",thumbnail);
	thumbnailMap.put("application/vnd.ms-powerpoint",thumbnail);
  
	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_video.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("video",thumbnail);
	thumbnailMap.put("video/quicktime",thumbnail);

	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_photograph.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("image",thumbnail);
  thumbnailMap.put("image/jpg",thumbnail);
  thumbnailMap.put("image/png",thumbnail);
  thumbnailMap.put("image/gif",thumbnail);

	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_portableDoc.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("portable document",thumbnail);

	thumbnail = new ThumbnailBean();
	thumbnail.setThumbnailId(""); //impossible database value
	thumbnail.setMetadataId(""); //impossible database value
	thumbnail.setLocation("images/thumbnails/thb_other.jpg");
	thumbnail.setFileWidth("80");
	thumbnail.setFileHeight("86");
	thumbnailMap.put("unknown",thumbnail);	
	unknownThumbnail = thumbnail;
    }

    /**
     * Takes the ThumbnailBean and checks it out or replaces it.
     * If there is a thumbnail specified,
     * then the method cleans the settings (changing nulls
     * to "") so that the output can be put directly into
     * an HTML page.  If there is no thumbnail setting, the
     * format is used to lookup a generic thumbnail.  If
     * no generic matches the format, a generic 'unknown'
     * thumbnail is used.  All generic thumbnails are stored 
     * in the images directory in the base of the heal
     * application heirarchy.  The second parameter should be
     * used to specify the relative path from the web page
     * being displayed to the application base.  For instance:
     * search/searchresults.jsp would pass "../" or ".."
     * The trailing slash is optional as the method will check
     * for it before appending the path.
     * NOTE: THIS METHOD RETURNS THE FULL URL TO ACCESS THE
     * THUMBNAIL FOR THIS CONTENT
     * 
     */
    public ThumbnailBean getThumbnail(ThumbnailBean thumbnail,
				      String format,
				      String pathToAppBase) {
	//added temp - JV
	ThumbnailBean temp = new ThumbnailBean();
	if (thumbnail == null) {
	    if (format != null) {
		thumbnail = (ThumbnailBean) thumbnailMap.get(format.toLowerCase());
	    }
	    if (thumbnail == null) {
		thumbnail = unknownThumbnail;
	    }
	    if (thumbnail != null && pathToAppBase != null) {
		//WE ARE GOING TO MODIFY THE LOCATION, SO
		//WE NEED TO CLONE THE THUMBNAIL...
		//thumbnail = (ThumbnailBean) thumbnail.clone();
		temp = (ThumbnailBean) thumbnail.clone();
		String newLoc;
		if (!pathToAppBase.endsWith("/")) {
		    newLoc = pathToAppBase+"/"+thumbnail.getLocation();
		} else {
		    newLoc = pathToAppBase+thumbnail.getLocation();
		}
		temp.setLocation(newLoc);
	    }
	} else {
	    //sanitize the string values
	    if (thumbnail.getLocation() == null) {
		thumbnail.setLocation("");
	    } else {

		//copied thumbnail into temp because we are rewriting location field - JV
		//previously thumbnail location kept growing every time reload was pressed - JV
		//This happened because we are appending http://baseurl to location everytime - JV

		temp = (ThumbnailBean) thumbnail.clone();
		temp.setLocation(fileLocator.getThumbnailURL(thumbnail.getLocation()));

	    }
	    if (thumbnail.getFileWidth() == null) {
		temp.setFileWidth("");
	    }
	    if (thumbnail.getFileHeight() == null) {
		temp.setFileHeight("");
	    }
	} 
	return temp;
    }

    /** 
     * Given a string and a maximum number of characters
     * to display.  If the provided string's length is
     * greater than the maximum to display, it will be 
     * abbreviated to the maximum and have an ellipsis
     * tagged onto the end.  The ellipsis will not count
     * against the maximum number of characters to 
     * display.  For instance: 
     * getAbbreviatedString("dimple",3) would return
     * a new String - "dim..."
     * If the passed String is null, "" is returned.
     * Otherwise, if the whole original String would
     * fit, it is simply returned back to the caller.
     * Finally, if the maxCharsToDisplay is <=0, only
     * the ellipsis is returned.
     */
    public String getAbbreviatedString(String aString, 
				       int maxCharsToDisplay) {
	String returnValue;
	int length;
	if (aString == null) {
	    returnValue = "";
	} else if (maxCharsToDisplay <=0) {
	    returnValue = "...";
	} else if ((aString.length()) > maxCharsToDisplay) {
	    returnValue = aString.substring(0,maxCharsToDisplay)+
		           "...";
	} else {
	    returnValue = aString;
	}
	return returnValue;
    }

    /**
     * Returns the user's name in a manner useful for display to the
     * user.  In this case it is:"lastname, firstname middleinitial".
     * If no name is found, an empty string is returned.
     */
    public String getUserDisplayName(String userId, UserRegistryBean registry)
	throws SQLException {
	if (userId == null || 
	    userId.trim().length() <= 0
	    || registry == null) {
	    return "";
	}
	UserBean user = null;
	user = registry.getUserFromID(userId);
	if (user != null) {
	    String first = convertNullToEmptyString(user.getFirstName());
	    String last = convertNullToEmptyString(user.getLastName());
	    String middle = convertNullToEmptyString(user.getMiddleInitial());
	    String result = last+", "+first+" "+middle;
	    return result;
	} else {
	    return "";
	}
    }

    /**
     * If the provided string is null, then "" is returned (empty string)
     * otherwise, the original string is returned.
     */
    public String convertNullToEmptyString(String aString) {
	if (aString == null) {
	    return "";
	} else {
	    return aString;
	}
    }

    /**
     * if bool is true, "yes" is returned - otherwise "no" is returned
     */
    public String booleanToYesNo(boolean bool) {
	if (bool) {
	    return "yes";
	}
	return "no";
    }
}













