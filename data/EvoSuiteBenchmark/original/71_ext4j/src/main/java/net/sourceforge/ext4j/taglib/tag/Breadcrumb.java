/**
 * @author luc
 */
package net.sourceforge.ext4j.taglib.tag;

/**
 * @author luc
 *
 */
public class Breadcrumb {

	private String mName;
	private String mURL;
	
	public Breadcrumb() {
		this(null,null);
	}
	
	public Breadcrumb(String pName) {
		this(pName, null);
	}
	
	public Breadcrumb(String pName, String pURL) {
		mName = pName;
		mURL = pURL;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getURL() {
		return mURL;
	}

	public void setURL(String pUrl) {
		mURL = pUrl;
	}

}
