/**
 * 
 */
package net.sourceforge.ext4j.taglib.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestParam {
	
	private Log mLogger = LogFactory.getLog(RequestParam.class);
	private String mName;
	private List<String> mValues;
	
	public RequestParam(String pName) {
		mName = pName;
		mValues = new ArrayList<String>();
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param pName The name to set.
	 */
	public void setName(String pName) {
		mName = pName;
	}
	
	public void addValue(String pValue) {
		mValues.add(pValue);
	}
	
	public String getValue(int pIndex) {
		return mValues.get(pIndex);
	}
	
	public int getValue(int pIndex, int pDefault) {
		int oResult = pDefault;
		if (mValues.size() == 0) return oResult;
		if (pIndex >= mValues.size() || pIndex < 0) return oResult;
		String oVal = null;
		try {
			oVal = getValue(pIndex);
			oResult = Integer.parseInt(oVal);
		} catch (Exception e) {
			mLogger.error("Could not parse value \"" + oVal + "\" as an integer.", e);
		}
		return oResult;
	}
	
	public int getTotalValues() {
		return mValues.size();
	}		
}