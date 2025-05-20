/**
 * @author luc
 */
package net.sourceforge.ext4j.taglib.bo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author luc
 *
 */
public class Request implements IRequest {

	private static final String FILE_SCHEME = "file:/";
	private static final String STRING_QMARK = "?";
	private static final String STRING_HASH = "#";
	private static final String STRING_AND = "&";
	private static final String STRING_EQUAL = "=";
	private static final String STRING_EMPTY = "";
	
	private Map<String, RequestParam> mParams = new Hashtable<String, RequestParam>();
	private boolean mIsWebRequest = false;
	private String mBaseURL;
	private String mHashValue; // string after the hash (#) sign
	private boolean mNoEmptyParams = false;
	private Log mLogger = LogFactory.getLog(Request.class);
	
	/**
	 * 
	 */
	public Request() {
		this( (String) null, false);
	}
	
	public Request(boolean pNoEmptyParams) {
		this((String) null, pNoEmptyParams);
	}
	
	public Request(HttpServletRequest pRequest) {
		this(pRequest, false);
	}
	
	public Request(HttpServletRequest pOrig, boolean pNoEmptyParams) {
		this((pOrig!=null)?pOrig.getRequestURL().toString() + ((pOrig.getQueryString() != null)?STRING_QMARK+pOrig.getQueryString():STRING_EMPTY):null, pNoEmptyParams);
	}
	
	public Request(String pUrl) {
		this(pUrl, false);
	}
	public Request(String pUrl, boolean pNoEmptyParams) {
		mNoEmptyParams = pNoEmptyParams;
		if (pUrl != null) parse(pUrl);
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#cleanEmptyParams()
	 */
	public IRequest cleanEmptyParams() {
		RequestParam oParam = null;
		Set<String> oKeys = new HashSet<String>();
		oKeys.addAll(mParams.keySet());
		for (String oName : oKeys) {
			oParam = mParams.get(oName);
			if (oParam.getTotalValues() == 0) {
				mParams.remove(oName);
				continue;
			}
			boolean oEmpty = false;
			for (int i = 0; i < oParam.getTotalValues() && !oEmpty; i++) {
				oEmpty = ((oParam.getValue(i) == null) || (oParam.getValue(i).trim().length() == 0));
			}
			if (oEmpty) mParams.remove(oName);
		}
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getBaseURL()
	 */
	public String getBaseURL() {
		return mBaseURL;
	}

	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#setBaseURL(java.lang.String)
	 */
	public IRequest setBaseURL(String pBaseURL) {
		mBaseURL = pBaseURL;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#isWebRequest()
	 */
	public boolean isWebRequest() {
		return mIsWebRequest;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getParams()
	 */
	public Collection<RequestParam> getParams() {
		return mParams.values();
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getParam(java.lang.String)
	 */
	public RequestParam getParam(String pName) {
		return getParam(pName, true);
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getParam(java.lang.String, boolean)
	 */
	public RequestParam getParam(String pName, boolean pIgnoreCase) {
		if (pName == null) return null;
		if (!pIgnoreCase) {
			return mParams.get(pName);
		}
		RequestParam oResult = null;
		for (String oKey : mParams.keySet()) {
			if (pName.equalsIgnoreCase(oKey)) {
				oResult = mParams.get(oKey);
				break;
			}
		}
		return oResult;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#toBaseURL()
	 */
	public IRequest toBaseURL() {
		mParams = new Hashtable<String, RequestParam>();
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getValue(java.lang.String, java.lang.String)
	 */
	public String getValue(String pName, String pDefault) {
		if (mParams.size() == 0) return pDefault;
		String oResult = pDefault;
		RequestParam oParam = getParam(pName, true);
		if (oParam != null && oParam.getTotalValues() > 0) oResult = oParam.getValue(0);
		return URLDecode(oResult);
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getValue(java.lang.String, int)
	 */
	public int getValue(String pName, int pDefault) {
		if (mParams.size() == 0) return pDefault;
		int oResult = pDefault;
		RequestParam oParam = getParam(pName, true);
		if (oParam != null && oParam.getTotalValues() > 0) oResult = oParam.getValue(0, pDefault);
		return oResult;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getHashValue()
	 */
	public String getHashValue() {
		return mHashValue;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#setHashValue(java.lang.String)
	 */
	public Request setHashValue(String pValue) {
		mHashValue = pValue;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#removeParam(java.lang.String)
	 */
	public IRequest removeParam(String pName) {
		if (mParams.size() > 0) {
			RequestParam oParam = getParam(pName, true);
			if (oParam != null) mParams.remove(oParam.getName());
		}
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#addParam(java.lang.String, java.lang.String)
	 */
	public IRequest addParam(String pName, String pValue) {
		RequestParam oParam = getParam(pName, true);
		if (oParam == null) {
			oParam = new RequestParam(pName);
			mParams.put(pName, oParam);
		}
		oParam.addValue(URLEncode(pValue));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#setParam(java.lang.String, java.lang.String)
	 */
	public IRequest setParam(String pName, String pValue) {
		RequestParam oParam = new RequestParam(pName);
		oParam.addValue(URLEncode(pValue));
		return setParam(oParam);
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#setParam(com.tripfilms.webtool.businessobject.Request.RequestParam)
	 */
	public IRequest setParam(RequestParam pParam) {
		if (pParam == null) return this;
		mParams.put(pParam.getName(), pParam);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#getQueryString()
	 */
	public String getQueryString() {
		StringBuilder oQS = new StringBuilder();
		RequestParam oParam = null;
		boolean oFirstPass = true;
		for (String oName : mParams.keySet()) {
			oParam = mParams.get(oName);
			for (int i = 0; i < oParam.getTotalValues(); i++) {
				if (oFirstPass) {
					oQS.append(STRING_QMARK);
				} else {
					oQS.append(STRING_AND);
				}
				oFirstPass = false;
				oQS.append(oParam.getName() + STRING_EQUAL + oParam.getValue(i));
			}
		}
		return oQS.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#toString()
	 */
	@Override
	public String toString() {
		return getBaseURL() + getQueryString() + ((mHashValue != null)?STRING_HASH + mHashValue:STRING_EMPTY);
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object pObj) {
		if (!(pObj instanceof Request)) return false;
		IRequest oComparedTo = (IRequest) pObj;
	 	// check if same base url
		if ((mBaseURL != null && oComparedTo.getBaseURL() == null) || (mBaseURL == null && oComparedTo.getBaseURL() != null) || (!mBaseURL.equalsIgnoreCase(oComparedTo.getBaseURL()))) return false;
		Collection<RequestParam> oParams = oComparedTo.getParams();
		// check if same number of params
		if (oParams.size() != getParams().size()) return false;
		// check each parameter
		for (RequestParam oParam : oParams) {
			RequestParam oMyParam = getParam(oParam.getName(), true);
			if (oMyParam == null) return false;
			// check number of values
			if (oMyParam.getTotalValues() != oParam.getTotalValues()) return false;
			// check values
			for (int i = 0; i < oMyParam.getTotalValues(); i++) {
				String oMyVal = oMyParam.getValue(i);
				boolean oFound = false;
				for (int j = 0; j < oParam.getTotalValues(); j++) {
					if (oParam.getValue(j).equals(oMyVal)) {
						oFound = true;
						break;
					}
				}
				if (!oFound) return false;
			}
		}
		if (!(mHashValue == null && oComparedTo.getHashValue() == null) && !(mHashValue.equals(oComparedTo.getHashValue()))) return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#hashCode()
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	protected void parse(String pUrl) {
		if (pUrl == null) return;
		if (pUrl.startsWith(FILE_SCHEME)) {
			return;
		} else {
			if (mLogger.isDebugEnabled()) mLogger.debug("Url \"" + pUrl +"\" is a web request");
			mIsWebRequest = true;
		}
		// Process "#" anchor
		String oUrl = pUrl;
		int oHashIndex = oUrl.indexOf(STRING_HASH); 
		if (oHashIndex > 0) {
			if (oHashIndex < oUrl.length()) {
				mHashValue = oUrl.substring(oHashIndex + 1);
			}
			oUrl = oUrl.substring(0, oHashIndex);
		}
		mBaseURL = oUrl;
		if (oUrl.indexOf(STRING_QMARK) < 0) return; // no query string to parse
		
		// Parsing query string
		mBaseURL = oUrl.substring(0, oUrl.indexOf(STRING_QMARK));
		String oQueryString = oUrl.substring(oUrl.indexOf(STRING_QMARK) + 1).trim();
		if (oQueryString.length() == 0) return;
		String[] oPairs = oQueryString.split(STRING_AND);
		for (String oName : oPairs) {
			String[] oParam = oName.split(STRING_EQUAL);
			if (oParam == null || oParam.length == 0) continue; // case: ?&from=1
			if (mLogger.isDebugEnabled()) mLogger.debug("Param/Value found for pair \"" + oName + "\" = " + oParam.length);
			// avoid adding empty params
			if (oParam.length == 1 && mNoEmptyParams) continue;
			String oEncodedValue = ((oParam.length == 2)?URLEncode(oParam[1]):STRING_EMPTY);
			if (mLogger.isDebugEnabled()) mLogger.debug("Adding param \"" + oParam[0] + "\" with value \"" + oEncodedValue + "\".");
			addParam(oParam[0], oEncodedValue);
		}
	}
	
	protected String URLEncode(String pValue) {
		if (pValue == null) return pValue;
		String oResult = pValue;
		try {
			// Problem with the following: it will encode something already encoded
			//oResult = URLEncoder.encode(pValue, "UTF-8");
			// New Version: encode manually the special characters
			oResult = pValue.replaceAll("&", "%26").replaceAll("\\?", "%3F").replaceAll("#", "%23").replaceAll("=", "%3D").replaceAll("\\s", "+");
		} catch (Exception e) {
			mLogger.fatal("Could not encode \"" + pValue + "\".", e);
		}
		return oResult;
	}
	
	protected String URLDecode(String pValue) {
		if (pValue == null) return pValue;
		String oResult = pValue;
		try {
			oResult = pValue.replaceAll("%26", "&").replaceAll("%3F", "\\?").replaceAll("%23", "#").replaceAll("%3D", "=").replaceAll("\\+", " ");
		} catch (Exception e) {
			mLogger.fatal("Could not decode \"" + pValue + "\".", e);
		}
		return oResult;
	}	
	
	/* (non-Javadoc)
	 * @see com.tripfilms.webtool.businessobject.IRequest#clone()
	 */
	@Override
	public IRequest clone() {
		IRequest oClone = new Request(getBaseURL() + getQueryString());
		return oClone;
	}

}
